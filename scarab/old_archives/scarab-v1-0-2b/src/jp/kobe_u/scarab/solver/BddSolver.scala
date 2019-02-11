package jp.kobe_u.scarab.solver

import jp.kobe_u.scarab._

class BddSolver extends SatSolver{
  var bdd: BDD = True

  def reset = ???
  def newVar(n: Int) = ???
  def setExpectedNumberOfClauses(n: Int) = ???
  def addAllClauses(clauses: Seq[Seq[Int]]) = 
    for (clause <- clauses) addClause(clause) 
  def addClause(lits: Seq[Int]) = {
    var sub: BDD = False
    for (l <- lits) {
      if (l < 0) sub = sub.or(Node.get(Math.abs(l),True,False))
      else sub = sub.or(Node.get(l,False,True))
    }
    bdd = bdd.and(sub)
  }
  def addAtLeast(lits: Seq[Int], degree: Int) = ???
  def addAtMost(lits: Seq[Int], degree: Int) = ???
  def addExactly(lits: Seq[Int], degree: Int) = ???
  def isSatisfiable = ???
  def isSatisfiable(assumps: Seq[Int]) = ???
  def model: Array[Int] = ???
  def model(v: Int) = ???
  def findModel: Array[Int] = ???
  def findModel(assumps: Seq[Int]): Array[Int] = ???
  def nVars = ???
  def nConstraints = ???
  def getStat = ???
  def setTimeout(time: Int) = ???
  def printInfos(out: java.io.PrintWriter, prefix: String) = ???
  def printFile(name: String, vars: Int) = BDD.dot(bdd,name)

}
