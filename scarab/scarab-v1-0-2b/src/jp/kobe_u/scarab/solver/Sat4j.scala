package jp.kobe_u.scarab.solver

import scala.collection.JavaConversions

import org.sat4j.minisat.SolverFactory
import org.sat4j.specs.ISolver
import org.sat4j.specs.ContradictionException
import org.sat4j.specs.TimeoutException
import org.sat4j.core.VecInt

/**
 * Wrapper class of [[http://www.sat4j.org "Sat4j solver"]].
 *
 * `org.sat4j.minisat.SolverFactory.newDefault` is used as the default solver.
 * You can replace it by re-assigning to the variable `solver`.
 *
 * `ContradictionException` and `TimeoutException` might happen when adding clauses or solving the problem.
 * It should be caught at the caller.
 *
 * @author Naoyuki Tamura
 * @see [[http://www.sat4j.org "Sat4j web site"]] for more details.
 */
class Sat4j (option: String) extends SatSolver {
  /** Sat4j solver.  `org.sat4j.minisat.SolverFactory.newDefault` is used as the default solver.
   */
  def this() = this("default")
  var sat4j: ISolver = null
  option match {
    case "default" => sat4j = SolverFactory.newDefault
    case "minisat" => sat4j = SolverFactory.newMiniSATHeap
    case "glucose" => sat4j = SolverFactory.newGlucose
    case "greedy" => sat4j = SolverFactory.newGreedySolver
    case "iterator" => {
      import org.sat4j.tools.ModelIterator
      sat4j = new ModelIterator(SolverFactory.newDefault)
    }
    case "dimacs" => {
      import org.sat4j.tools.DimacsStringSolver
      sat4j = new DimacsStringSolver()
    }
  }

  def reset =
    sat4j.reset
  def newVar(n: Int) =
    sat4j.newVar(n)
  def setExpectedNumberOfClauses(n: Int) =
    sat4j.setExpectedNumberOfClauses(n)
  def addAllClauses(clauses: Seq[Seq[Int]]) =
    for (clause <- clauses) addClause(clause) 
  def addClause(lits: Seq[Int]) = 
    sat4j.addClause(new VecInt(lits.toArray))
  def addAtLeast(lits: Seq[Int], degree: Int) =
    sat4j.addAtLeast(new VecInt(lits.toArray), degree)
  def addAtMost(lits: Seq[Int], degree: Int) =
    sat4j.addAtMost(new VecInt(lits.toArray), degree)
  def addExactly(lits: Seq[Int], degree: Int) =
    sat4j.addExactly(new VecInt(lits.toArray), degree)
  def isSatisfiable = 
    sat4j.isSatisfiable
  def isSatisfiable(assumps: Seq[Int]) =
    sat4j.isSatisfiable(new VecInt(assumps.toArray))
  def model: Array[Int] =
    sat4j.model
  def model(v: Int) =
    sat4j.model(v)
  def findModel: Array[Int] =
    sat4j.findModel
  def findModel(assumps: Seq[Int]): Array[Int] =
    sat4j.findModel(new VecInt(assumps.toArray))
  def nVars =
    sat4j.nVars
  def nConstraints =
    sat4j.nConstraints
  def getStat =
    JavaConversions.mapAsScalaMap(sat4j.getStat).toMap
  def setTimeout(time: Int) = 
    sat4j.setTimeout(time)
  def printInfos(out: java.io.PrintWriter, prefix: String) = 
    sat4j.printInfos(out, prefix)
  def printFile(name: String, vars: Int) = sat4j match {
    case sol: org.sat4j.tools.DimacsStringSolver => {
      import java.io.PrintWriter
      sol.getOut.insert(0,Seq("p","cnf",vars,nConstraints).mkString(" "))
      val out = new PrintWriter(name)
      out.println(sol.getOut)
      out.close    
    }
    case _ => {
      throw new UnsupportedOperationException("This SAT Solver does not support printFile method")
    }
  }
}
