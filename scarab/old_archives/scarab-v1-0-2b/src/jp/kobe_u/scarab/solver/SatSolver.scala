package jp.kobe_u.scarab.solver

/** `SatSolver` is an abstract class for SAT solver.
 * Currently, only the [[jp.kobe_u.scarab.solver.Sat4j]] is its implementation.
 */
abstract class SatSolver {
  def reset: Unit
  def newVar(n: Int): Unit
  def setExpectedNumberOfClauses(n: Int): Unit
  def addAllClauses(clauses: Seq[Seq[Int]]): Unit
  def addClause(lits: Seq[Int]): Unit
  def addAtLeast(lits: Seq[Int], degree: Int): Unit
  def addAtMost(lits: Seq[Int], degree: Int): Unit
  def addExactly(lits: Seq[Int], degree: Int): Unit
  def isSatisfiable: Boolean
  def isSatisfiable(assumps: Seq[Int]): Boolean
  def model: Array[Int]
  def model(v: Int): Boolean
  def findModel: Array[Int]
  def findModel(assumps: Seq[Int]): Array[Int]
  def nVars: Int
  def nConstraints: Int
  def getStat: Map[String,Number]
  def setTimeout(time: Int)
  def printInfos(out: java.io.PrintWriter, prefix: String)
  def printFile(name: String, vars: Int)
}

