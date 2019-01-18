package jp.kobe_u.scarab

import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._

/**
 * Trait for Scarab DSL which provides methods for CSP and CSP solver.
 */
trait ScarabTrait {

  def csp: CSP
  def satSolver: SatSolver
  def encoder: Encoder
  def solver: Solver

  def use(newEncoder: Encoder): Unit
  def use(newSatSolver: SatSolver): Unit
  def use(newSolver: Solver): Unit

  /* */
  def int(x: Var, a: Int, b: Int) = csp.int(x,a,b)
  /* */
  def bool(p: Bool) = csp.bool(p)
  /* */  
  def add(c: Constraint) = csp.add(c)
  /* */  
  def dom(v: Var) = csp.dom(v)
  /* */  
  def commit = csp.commit
  /* */  
  def show = csp.show
  /* */  
  def rollback = csp.rollback
  /* */
  def find = solver.find
  /* */
  def findNext = solver.findNext
  /* */
  def find(con: Constraint) = solver.find(con)
  /* */
  def find(cons: Seq[Constraint]) = solver.find(cons)
  /* */
  def solution = solver.solution
  /* */
  def printFile(name: String) = solver.printFile(name)
  /* */
  def alldiff(xs: Seq[Term]) = {
    def check(xs: Seq[Term]): Boolean = 
      xs.forall(i => i match {case i: Var => { true } ; case _ => { false } } )

    xs match {
      case xs: Seq[Var] => {
	if (check(xs)) {
	  val lb = for (x <- xs) yield csp.dom(x).lb
	  val ub = for (x <- xs) yield csp.dom(x).ub
    
	  val ph = 
	    And(Or(for (x <- xs) yield !(x < lb.min+xs.size-1)),
  		Or(for (x <- xs) yield !(x > ub.max-xs.size+1)))
	  def perm = 
	    And(for (num <- lb.min to ub.max) 
		yield Or(for (x <- xs) yield x === num))
	  val extra = if (ub.max-lb.min+1 == xs.size) And(ph,perm) else ph
    
	  And(And(for (Seq(x, y) <- xs.combinations(2)) yield x !== y),extra)
	} else
	  And(for (Seq(x, y) <- xs.combinations(2)) yield x !== y)
      }
      case _ => And(for (Seq(x, y) <- xs.combinations(2)) yield x !== y)
    }
  }

}

class Scarab (val csp: CSP, 
	      var satSolver: SatSolver,
	      var encoder: Encoder,
	      var solver: Solver) extends ScarabTrait {

  def this(csp: CSP, satSolver: SatSolver, encoder: Encoder) = 
    this(csp, satSolver, encoder, new Solver(csp,satSolver,encoder))
  def this(csp: CSP, satSolver: SatSolver) = 
    this(csp, satSolver, new OrderEncoder(csp,satSolver))
  def this(csp: CSP) = this(csp, new Sat4j())
  def this() = this(CSP())

  def use(newEncoder: Encoder) = {
    encoder = newEncoder
    satSolver = encoder.satSolver
    solver = new Solver(csp,satSolver,encoder)
  }
  def use(newSatSolver: SatSolver) = {
    satSolver = newSatSolver
    encoder = new OrderEncoder(csp,satSolver)
    solver = new Solver(csp,satSolver,encoder)
  }
  def use(newSolver: Solver) = 
    solver = newSolver
}

/**
 * Singleton for Scarab Applications.
 */
object sapp extends ScarabTrait {
  import scala.util.DynamicVariable
  val scarabVar = new DynamicVariable[Scarab](new Scarab)
  def csp = scarabVar.value.csp
  def satSolver = scarabVar.value.satSolver
  def encoder = scarabVar.value.encoder
  def solver = scarabVar.value.solver

  def use(newEncoder: Encoder) = scarabVar.value.use(newEncoder)
  def use(newSatSolver: SatSolver) = scarabVar.value.use(newSatSolver)
  def use(newSolver: Solver): Unit = scarabVar.value.use(newSolver)
  def using(scarab: Scarab = new Scarab)(block: => Unit) = 
    scarabVar.withValue(scarab) { block }
}
