package jp.kobe_u.scarab.solver

import jp.kobe_u.scarab.csp._

/** `CardOrderEncoder` is a class for translating CSP to SAT by order */
/** encoding with using `addAtMost` and `addAtLeast` methods of Sat4j.
 */
class CardOrderEncoder(csp: CSP, satSolver: SatSolver) extends OrderEncoder(csp, satSolver) {

  override def add(c: Constraint) {
    for (lits <- simplifier.simplify(c) if lits.size > 0) {
      if (lits.size == 1) {
	csp.add(lits(0))
	val clauses = encodeCard(lits)
	if (!clauses.isEmpty)
	  addAllClauses(clauses)
      } else {
	csp.add(Or(lits))
	val clauses = encode(lits)
	addAllClauses(clauses)
      }
    }
  }

  def encodeCard(c: Seq[Literal]): Seq[Seq[Int]] =
    if (c.isEmpty) Seq(Seq.empty)
    else {
      for {
  	clause0 <- this.encode(c.tail)
  	clause <- encodeCard(c.head, clause0)
      } yield clause
    }

  def encodeCard(lit: Literal, clause0: Seq[Int]): Seq[Seq[Int]] = lit match {
    case p: Bool => Seq(code(p) +: clause0)
    case Not(p) => Seq(-code(p) +: clause0)
    case LeZero(sum) => {
      val axs = sum.coef.toSeq.map(xa => (xa._2,xa._1))
      val dom01 = Domain(0,1)
      if (axs.forall(n => (Math.abs(n._1)==1)&&(csp.dom(n._2)==dom01))) {
	if (sum.b < 0) {
	  val vset: Seq[Int] = axs.map(n => if (n._1 < 0) code(n._2) else -1*code(n._2))
	  println("atMost: "+vset.mkString("{",",","}")+" " + Math.abs(sum.b))
	  satSolver.addAtMost(vset, Math.abs(sum.b))
	} else {
	  val vset: Seq[Int] = axs.map(n => if (n._1 < 0) -1*code(n._2) else code(n._2))
	  println("atLeast: "+vset.mkString("{",",","}")+" " + Math.abs(sum.b))
	  satSolver.addAtLeast(vset, sum.b)
	}
	Seq.empty
      } else 
	encodeLe(axs, -sum.b, clause0)

    }
  }
}
