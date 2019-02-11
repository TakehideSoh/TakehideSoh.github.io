package jp.kobe_u.scarab.solver

import jp.kobe_u.scarab.csp._

/**
 * Classes for CSP solvers.
 * @author Naoyuki Tamura
 */

/** `Solver` is a class for CSP solvers.
 * It encodes the given CSP to a SAT instance by using the specified encoder,
 * and solves the encoded SAT instacne by using the specified SAT solver.
 * [[jp.kobe_u.scarab.solver.OrderEncoder]] is used as the default encoder, and
 * [[jp.kobe_u.scarab.solver.Sat4j]] is used as the default SAT solver.
 *
 * Typical usage of the solver is as follows.
 * {{{
 *     val solver = new Solver(csp)
 *     if (solver.find) {
 *       println(solver.solution)
 *     }
 * }}}
 *
 * The `find` method does the following.
 *   1. When it is called at the first time, the whole CSP is encoded by the `encodeCSP` method of [[jp.kobe_u.scarab.solver.Encoder]],
 *      and generated clauses are added to the SAT solver by the `addClause` method of [[jp.kobe_u.scarab.solver.SatSolver]].
 *   1. The `isSatisfiable` method of [[jp.kobe_u.scarab.solver.SatSolver]] is called.
 *   1. When a solution is found, the `decode` method of [[jp.kobe_u.scarab.solver.Encoder]] is called to get the solution
 *      and the solution is set to the `solution` variable.
 *      Otherwise, the `solution` variable is set to `null`.
 * 
 * The `add` method does the following.
 *   1. The constraint is preprocessed before encoding, and the translated constraints are added to the CSP.
 *      [[jp.kobe_u.scarab.solver.Simplifier]] is the preprocessor of [[jp.kobe_u.scarab.solver.OrderEncoder]].
 *   1. The translated constraints are encoded by [[jp.kobe_u.scarab.solver.Encoder]],
 *      and generated clauses are added to the SAT solver by the `addClause` method of [[jp.kobe_u.scarab.solver.SatSolver]].
 * 
 * {{{
 *     val solver = new Solver(csp)
 *     if (solver.find) {
 *       solver.add(new constraint to be added)
 *       if (solver.find) {
 *         println(solver.solution)
 *       }
 *     }
 * }}}
 *
 * The SAT solver keeps working during the above process.
 * Therefore, we can get the benefit of reusing learnt clauses of the SAT solver at the second `find`.
 * 
 * @constructor Creates a new solver
 * @param csp the CSP to be solved
 * @param satSolver the SAT solver to be used
 * @see [[jp.kobe_u.scarab]].
 */
class Solver(val csp: CSP, 
	     val satSolver: SatSolver = new Sat4j(), 
	     val encoder: Encoder) {
  /** Encoder to be used ([[jp.kobe_u.scarab.solver.OrderEncoder]] is used as the default). */
  // var encoder: Encoder = new OrderEncoder(csp, satSolver)
  /** Remembers the solution (assignment) found by `find` method.
   * It is set to `null` at the beginning and when no solutions are found. */
  var solution: Assignment = null
  /** Remembers the solution (assignment) found by `find` method.
   * It keeps the solutions last time found. */
  var solutionLast: Assignment = null
  // var sol: Assignment = null // to be implemented simple solution
  //  sol = (solution.intMap.filter(t => t._1.name match...
  /** Finds a solution (assignment) of the given CSP. */
  def find: Boolean = {
    // if (!csp.changed)
    //   addBlockClause
    encoder.encodeCSP
    csp.changed = false
    val result = satSolver.isSatisfiable
    solution = if (result) encoder.decode else null
    solutionLast = if (result) encoder.decode else solutionLast
    result
  }

  def findNext: Boolean = {
    addBlockClause
    find
  }

  /** enumeration method for Sat4j("iterate") */
  def enumerate: Boolean = {
    encoder.encodeCSP
    val result = satSolver.isSatisfiable
    // Sat4j("iterate") needs calling model() for enumeration
    if (result) satSolver.model
    solution = if (result) encoder.decode else null
    solutionLast = if (result) encoder.decode else solutionLast
    result
  }

  def addBlockClause = {
      val cs1 = for (x <- csp.variables)
		yield (x !== x.value(solution))
      val cs2 = for (p <- csp.bools)
              yield if (solution(p)) Not(p) else p
      csp.add(Or(Or(cs1), Or(cs2))) 
  }

  /** With multiple assumptions, finds a solution (assignment) of the
   * given CSP (experimental)
   * */
  def find(cons: Seq[Constraint]): Boolean = {
    encoder.encodeCSP
    val result = satSolver.isSatisfiable(convert(cons))
    solution = if (result) encoder.decode else null
    solutionLast = if (result) encoder.decode else solutionLast
    result
  }

  /** With an assumption, finds a solution (assignment) of the given
   *  CSP. (experimental)
   * */
  def find(con: Constraint): Boolean = find(Seq(con))

  /** Decompose assumption into literals (experimental) */
  def convert (cons: Seq[Constraint]): Seq[Int] = {
    def toSat(c: Constraint): Seq[Seq[Int]] = {
      c match {
	case p: Bool => encoder.encode(Seq(p))
	case Not(p) => encoder.encode(Seq(Not(p)))
	case LeZero(sum) => encoder.encode(Seq(LeZero(sum)))
	case And(LeZero(sum1),LeZero(sum2)) => {
	  var llits = encoder.encode(Seq(LeZero(sum1)))
	  llits ++ encoder.encode(Seq(LeZero(sum2)))
	}
	case _ => { 
	  throw new IllegalArgumentException("un-supported assumption form") 
	  Seq.empty 
	  // to be implemented
	}
      }
    }
    def toAssumps(llits: Seq[Seq[Int]]): Seq[Int] = {
      llits match {
	case Seq(Seq(x)) => Seq(x)
	case Seq(Seq(x),Seq(y)) => Seq(x,y)
	case _ => { 
	  throw new IllegalArgumentException("un-supported assumption form") 
	  Seq.empty 
	  // to be implemented
	}
      }
    }
    for {
      c <- cons
      lit <- toAssumps(toSat(c)) if lit != encoder.TrueLit
    } yield lit
  }

  /** Adds the constraint `c` to CSP after preprocessing and encodes it. */
  def add(c: Constraint) {
    encoder.add(c)
  }
  /* */
  def printFile(name: String) = {
    encoder.encodeCSP    
    satSolver.printFile(name,encoder.satVariableCount)
  }
}


/**
 * Factory for default solver.
 *
 * [[jp.kobe_u.scarab.solver]] is returned as the default solver.
 * @see [[jp.kobe_u.scarab.solver]]
 */
object DefaultSolver {
  def apply(csp: CSP): Solver = {
    val satSolver = new Sat4j()
    val encoder = new OrderEncoder(csp,satSolver)
    val solver = new Solver(csp,satSolver,encoder)
    solver
  }
}
