import java.lang.management.ThreadMXBean
import java.lang.management.ManagementFactory

import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._

object LSCardBench {
  def solveLatin(n: Int): Seq[String] = {
    var csp = CSP()
    var satSolver: SatSolver = new Sat4j("glucose")
    var encoder: Encoder = new CardOrderEncoder(csp,satSolver)
    var solver = new Solver(csp,satSolver,encoder)

    satSolver.setTimeout(3600)
    for (i <- 1 to n; j <- 1 to n)  csp.int('x(i,j),1,n) 
    for (i <- 1 to n) csp.add(alldiff((1 to n).map('x(i,_))))
    for (j <- 1 to n) csp.add(alldiff((1 to n).map('x(_,j))))
    for (i <- 1 to n) csp.add(alldiff((1 to n).map(j => 'x(j,(i+(j-1)*(n-1))%n+1))))
    for (i <- 1 to n) csp.add(alldiff((1 to n).map(j => 'x(j,((i+j-1)%n+1)))))
    // additional clauses: at least constraint for each alldifferent

    var res: Boolean = false
    try {
      res = solver.find
    } catch {
      case _: org.sat4j.specs.TimeoutException => {
     	return Seq(satSolver.nVars.toString,satSolver.nConstraints.toString,"T.O.")
      }
    }
    // Solve CSP
    if (res) {
      println(solver.solution)
      Seq(satSolver.nVars.toString,satSolver.nConstraints.toString,"SAT")
    } else {
      Seq(satSolver.nVars.toString,satSolver.nConstraints.toString,"UNSAT")
    }
  }
  def main(args: Array[String]) = {
    var threadMXBean: ThreadMXBean = ManagementFactory.getThreadMXBean()

     def run (n: Int) {
      var t1 = threadMXBean.getCurrentThreadCpuTime()
      val info = Seq(n) ++ solveLatin(n)
      var t2 = threadMXBean.getCurrentThreadCpuTime()
      var parse = (t2 - t1) / scala.math.pow(10,9)
      println("CSV,"+ info.mkString(",") + "," + parse)
    }
    
    for (n <- args(0).toInt to args(1).toInt) {
      run(n)
    }
  }
}
