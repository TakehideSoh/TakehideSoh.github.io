package jp.kobe_u.scarab.solver

import jp.kobe_u.scarab.csp._

/** `Encoder` is an abstract class for encoding CSP to SAT.
 * Currently, only the [[jp.kobe_u.scarab.solver.OrderEncoder]] is its implementation.
 *
 * The `encodeCSP` method encodes the whole CSP when it is called at the first time
 * (or whenever there is some change (addition or rollback of
  constraints) in CSP).
 * Generated clauses are added to the SAT solver by the `addClause` method of this class
 * which calls the `addClause` method of [[jp.kobe_u.scarab.solver.SatSolver]].
 * 
 * ==Implementation Issues==
 * ===Encoding integer variables===
 * When encoding an integer variable to SAT, a multiple number of SAT variables is required in general.
 * Each integer variable `x` has its ''code'' which is the minimum SAT variable number used for `x`.
 * Following the classical DIMACS CNF format, the code is a positive number beginning from one.
 * In `encodeCSP` method,  the code is assigned for each integer variable and it is resistered to `varCodeMap`.
 * The code value regisitered in `varCodeMap` can be obtained by `code` method.
 * 
 * The following methods should be implemented in the subclass.
 *   - `satVariablesSize(x: Var): Int` : returns the number of SAT variables required to encode `x`.
 *   - `encode(x: Var): Seq[Seq[Int]]` : returns the list of clauses required to encode `x`.
 * 
 * ===Encoding Boolean variables===
 * In `encodeCSP` method,  the code is assigned for each Boolean variable and it is resistered to `boolCodeMap`.
 * The code value regisitered in `boolCodeMap` can be obtained by `code` method.
 * 
 * ===Encoding constraints===
 * It might be necessary to perform preprocessing to the given CSP before encoding.
 * [[jp.kobe_u.scarab.solver.Simplifier]] does such a transformation to clausal form, that is,
 * constraints are transformed to `Seq[Seq[Literal]]` data.
 * 
 * The following methods should be implemented in the subclass.
 *   - `encode(lit: Literal, clause0: Seq[Int]): Seq[Seq[Int]]`
 *   - `add(c: Constraint): Unit`
 *
 * ===Encoding CSP===
 * The `encodeCSP` method performs the encoding of the whole CSP when
 it is called at the first time (or whenever there is some change (addition or rollback of constraints) in CSP)
 * It is implemented as follows.
 *   1. If `firstTime` is false, it returns immediately.
 *   1. For each integer variable `x` in the CSP, its code is registered to `varCodeMap`, and
 *      `satVariableCount` is increased by `satVariablesSize(x)`.
 *   1. For each Boolean variable `p` in the CSP, its code is registered to `boolCodeMap`, and
 *      `satVariableCount` is increased by one.
 *   1. For each integer variable `x` in the CSP, `encode(x)` is called.
 *   1. All constraints are removed from the CSP.
 *   1. For each constraint `c` removed from the CSP, `add(c)` is called.
 *   1. The `firstTime` flag is set to false.
 * 
 * ===Decoding===
 * The following methods should be implemented in the subclass.
 *   - `decode(x: Var): Int` : returns the value of `x` from the satisfiable assignment found by the SAT solver.
 * 
 * @param csp the CSP to be encoded
 * @param satSolver the SAT solver where the generated clauses are stored
 */
abstract class Encoder(csp: CSP, val satSolver: SatSolver) {
  /** Returns true when the CSP is not encoded */
  var firstTime = true
  /** Remembers the number of SAT variables used */
  var satVariableCount = 0
  /** Remembers the number of SAT clauses used */
  var satClauseCount = 0
  /** Remembers the code of each integer variable in CSP */
  var varCodeMap: Map[Var,Int] = Map.empty
  /** Remembers the code of each Boolean variable in CSP */
  var boolCodeMap: Map[Bool,Int] = Map.empty
  /** Represents the integer constant for true literal (Integer.MAX_VALUE).
   * It is only used during the encoding process, and does not appear in the generated clause finally. */
  val TrueLit = Integer.MAX_VALUE
  /** Represents the integer constant for false literal (-Integer.MAX_VALUE).
   * It is only used during the encoding process, and does not appear in the generated clause finally. */
  val FalseLit = -TrueLit

  /** modified
   * 1. remember when encode is occurred
   *  - variablesSizeEncoded
   *  - boolsSizeEncoded
   *  - constraintsSizeEncoded
   * encode the difference from those sizes
   *
   * 1. correct the commit point difference caused by simplify
   *
   * 1. reset SatSolver and reconstruct CNF until commit point when
   * rollback is called
  */
  var variablesSizeEncoded = 0
  var boolsSizeEncoded = 0
  var constraintsSizeEncoded = 0

  /** Resets the encoder. */
  def reset {
    satSolver.reset
    firstTime = true
    satVariableCount = 0
    satClauseCount = 0
    varCodeMap = Map.empty
    boolCodeMap = Map.empty
    // added by Soh
    variablesSizeEncoded = 0
    boolsSizeEncoded = 0
    constraintsSizeEncoded = 0
  }

  /** Adds SAT clause by using `addClause` method of [[jp.kobe_u.scarab.solver.SatSolver]] after removing `TrueLit` and `FalseLit`.
   * When the clause contains `TrueLit`, the clause is not added.
   * When the clause only contains `FalseLit`, two clauses 1 and -1
  are added. */

  def addClause(clause: Seq[Int]) {
    val clause1 = clause.filter(_ != FalseLit)
    if (clause1.contains(TrueLit)) {
    } else if (clause1.isEmpty) {
      satSolver.addClause(Seq(1))
      satSolver.addClause(Seq(-1))
      satClauseCount += 2
    } else {
      satSolver.addClause(clause1)
      satClauseCount += 1
    }
  }

  /** Adds all clauses by calling `addClause` method of this class. */
  def addAllClauses(clauses: Seq[Seq[Int]]) {
    for (clause <- clauses if ! clause.contains(TrueLit))
      addClause(clause)
  }

  /** Creates a new Boolean variabe, adds it to the CSP, and registers it to `boolCodeMap`. */
  def newBool = {
    val p = csp.newBool
    boolCodeMap += p -> (satVariableCount + 1)
    satVariableCount += 1
    p
  }

  /** Returns the lower bound value of `x`. */
  def lb(x: Var): Int = csp.dom(x).lb
  /** Returns the upper bound value of `x`. */
  def ub(x: Var): Int = csp.dom(x).ub
  /** Returns the lower bound value of `a*x`. */
  def lb(a: Int, x: Var): Int = if (a > 0) a * lb(x) else a * ub(x)
  /** Returns the upper bound value of `a*x`. */
  def ub(a: Int, x: Var): Int = if (a > 0) a * ub(x) else a * lb(x)
  /** Returns the lower bound value of the linear summation. */
  def lb(axs: Seq[(Int,Var)]): Int = axs.map { case (a, x) => lb(a, x) }.sum
  /** Returns the upper bound value of the linear summation. */
  def ub(axs: Seq[(Int,Var)]): Int = axs.map { case (a, x) => ub(a, x) }.sum

  /** Returns the value of `floor(b / a)`.
   * This is used because math.floor is slow. */
  def floorDiv(b: Int, a: Int) =
    if (a > 0) {
      if (b >= 0) b/a else (b-a+1)/a
    } else {
      if (b >= 0) (b-a-1)/a else b/a
    }

  /** Returns the value of `ceil(b / a)`.
   * This is used because math.ceil is slow. */
  def ceilDiv(b: Int, a: Int) =
    if (a > 0) {
      if (b >= 0) (b+a-1)/a else b/a
    } else {
      if (b >= 0) b/a else (b+a+1)/a
    }

  /** Returns the number of SAT variables required to encode `x`. */
  def satVariablesSize(x: Var): Int

  /** Returns the code of `x`. */
  def code(x: Var) = varCodeMap(x)

  /** Returns the code of `p`. */
  def code(p: Bool) = boolCodeMap(p)

  /** Returns the list of clauses generated by encoding integer variable `x`. */
  def encode(x: Var): Seq[Seq[Int]]

  /** Returns the list of clauses generated by encoding literal `lit`.
   * `clause0` should be added to each clause generated. */
  def encode(lit: Literal, clause0: Seq[Int]): Seq[Seq[Int]]

  /** Returns the list of clauses generated by encoding clausal form constraint `c`. */
  def encode(c: Seq[Literal]): Seq[Seq[Int]] =
    if (c.isEmpty) Seq(Seq.empty)
    else {
      for {
	clause0 <- encode(c.tail)
	clause <- encode(c.head, clause0)
      } yield clause
    }

  /** Adds the constraint to CSP after preprocessing and encodes it. */
  def add(c: Constraint): Unit

  /** Encodes the whole CSP when `firstTime` is true.  Otherwise, does nothing. */
  def encodeCSP {
    // modified by Soh
    if (csp.rollbackHappen) {
      // rollback
      satSolver.reset
      variablesSizeEncoded = 0
      boolsSizeEncoded = 0
      constraintsSizeEncoded = 0
      csp.rollbackHappen = false
    }

    for (n <- variablesSizeEncoded to csp.variables.size-1) {
      varCodeMap += csp.variables(n) -> (satVariableCount + 1)
      satVariableCount += satVariablesSize(csp.variables(n))
    }
    for (n <- boolsSizeEncoded to csp.bools.size-1) {
      boolCodeMap += csp.bools(n) -> (satVariableCount + 1)
      satVariableCount += 1
    }
    for (n <- variablesSizeEncoded to csp.variables.size-1)
    addAllClauses(encode(csp.variables(n)))
    
    val oldConstraints = csp.constraints 
    csp.constraints = csp.constraints.take(constraintsSizeEncoded)
    for (n <- constraintsSizeEncoded to oldConstraints.size-1) {
      add(oldConstraints(n))
      if (n == csp.constraintsSize-1) { 
      	csp.constraintsSize = csp.constraints.size
      	csp.boolsSize = csp.bools.size
      }
    }
    variablesSizeEncoded = csp.variables.size
    boolsSizeEncoded = csp.bools.size
    constraintsSizeEncoded = csp.constraints.size
  }

  /** Returns the value of `x` from the satisfiable assignment found by the SAT solver. */
  def decode(x: Var): Int

  /** Returns the satisfiable assignment of CSP from the satisfiable assignment found by the SAT solver. */
  def decode: Assignment = {
    var varAssign: Map[Var,Int] = Map.empty
    var boolAssign: Map[Bool,Boolean] = Map.empty
    for (x <- csp.variables)
      varAssign += x -> decode(x)
    for (p <- csp.bools)
      boolAssign += p -> satSolver.model(boolCodeMap(p))
    // varAssign = varAssign.filter(! _._1.aux)
    // boolAssign = boolAssign.filter(! _._1.aux)
    Assignment(varAssign, boolAssign)
  }


}
