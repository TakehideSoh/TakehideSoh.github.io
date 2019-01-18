package jp.kobe_u.scarab.solver

import jp.kobe_u.scarab.csp._

/** `OrderEncoder` is a class for translating CSP to SAT by order encoding.
 */
class OrderEncoder(csp: CSP, satSolver: SatSolver) extends Encoder(csp, satSolver) {
  val simplifier = new Simplifier(this)

  // x <= b
  def le(x: Var, b: Int): Int =
    if (b < lb(x)) FalseLit
    else if (b >= ub(x)) TrueLit
    else code(x) + b - lb(x)

  // a * x <= b
  def le(a: Int, x: Var, b: Int): Int =
    if (a > 0) le(x, floorDiv(b, a))
    else -le(x, ceilDiv(b, a) - 1)

  def satVariablesSize(x: Var) =
    csp.dom(x).ub - csp.dom(x).lb

  def encode(x: Var): Seq[Seq[Int]] =
    for (b <- lb(x)+1 to ub(x)-1)
      yield Seq(-le(x, b-1), le(x, b))

  def encodeLe(axs: Seq[(Int,Var)], c: Int, clause0: Seq[Int]): Seq[Seq[Int]] = axs match {
    case Seq() =>
      if (c >= 0) Seq.empty else Seq(clause0)
    case Seq((a,x)) =>
      Seq(clause0 :+ le(a, x, c))
    case Seq((a,x), axs1 @ _*) => {
      if (a > 0) {
	val ub0 = floorDiv(c-lb(axs1), a)
	for {
	  b <- lb(x) to math.min(ub(x), ub0) + 1
	  // b <- lb(x) to ub(x) + 1
	  lit = le(x, b-1)
	  if lit != TrueLit
	  clause <- encodeLe(axs1, c-a*b, lit +: clause0)
	} yield clause
      } else {
	val lb0 = ceilDiv(c-lb(axs1), a)
	for {
	  b <- math.max(lb(x), lb0) - 1 to ub(x)
	  // b <- lb(x) - 1 to ub(x)
	  lit = -le(x, b)
	  if lit != TrueLit
	  clause <- encodeLe(axs1, c-a*b, lit +: clause0)
	} yield clause
      }
    }
  }
  
  def encode(lit: Literal, clause0: Seq[Int]): Seq[Seq[Int]] = lit match {
    case p: Bool => Seq(code(p) +: clause0)
    case Not(p) => Seq(-code(p) +: clause0)
    case LeZero(sum) => {
      val axs = sum.coef.toSeq.map(xa => (xa._2,xa._1))
      encodeLe(axs, -sum.b, clause0)
    }
  }

  def add(c: Constraint) {
    for (lits <- simplifier.simplify(c) if lits.size > 0) {
      if (lits.size == 1)
	csp.add(lits(0))
      else
	csp.add(Or(lits))
      val clauses = encode(lits)
      addAllClauses(clauses)
    }
  }

  def decode(x: Var) =
    (lb(x) to ub(x)-1).find(b => satSolver.model(le(x, b))).getOrElse(ub(x))

}
