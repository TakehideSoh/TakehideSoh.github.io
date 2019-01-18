import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._
import scala.math.{sqrt, ceil}
import java.lang.management.ThreadMXBean
import java.lang.management.ManagementFactory


object Spp {
    def solveSpp (N: Int, L: Int): Seq[String] = {
    // Create CSP, SatSolver, Encoder, and SAT-based CSP Solver
    val csp = new CSP()
    val satSolver = new Sat4j()
    satSolver.setTimeout(3600)
    val encoder = new OrderEncoder(csp,satSolver)
    val solver = new Solver(csp,satSolver,encoder)

    // Define Integer Variable
    for (i <- 0 to N) {
      csp.int('x(i), 0, L-i)
      csp.int('y(i), 0, L-i)
    }

    val h = Bool("h"); val v = Bool("v")
    for (i <- 0 to N; j <- i+1 to N) {
      csp.bool(h(i,j))
      csp.bool(v(i,j))
      csp.add(! h(i,j) || ('x(i) + i <= 'x(j)) || ('x(j) + j <= 'x(i)))
      csp.add(! v(i,j) || ('y(i) + i <= 'y(j)) || ('y(j) + j <= 'y(i)))
      csp.add( h(i,j) || v(i,j) )
    }

    var res: Boolean = false
    try {
      res = solver.find
    } catch {
      case _: org.sat4j.specs.TimeoutException => {
	return Seq(satSolver.nVars.toString,satSolver.nConstraints.toString,"T.O.")
      }
    }
    if (res)
      Seq(satSolver.nVars.toString,satSolver.nConstraints.toString,"SAT")
    else
      Seq(satSolver.nVars.toString,satSolver.nConstraints.toString,"UNSAT")
  }
  
  def run (n: Int, l: Int) {
    def point(t2: Long, t1: Long): Double = (t2 - t1) / scala.math.pow(10,9)    
    val threadMXBean: ThreadMXBean = ManagementFactory.getThreadMXBean()    
    val t1 = threadMXBean.getCurrentThreadCpuTime()
    val info = Seq(n,l) ++ solveSpp(n,l)
    val t2 = threadMXBean.getCurrentThreadCpuTime()
    println(info.mkString(",") + "," + point(t2,t1))
  }

  def main(args: Array[String]) {
    def areaSum (n: Int) : Int = if (n==1) 1  else n*n + areaSum(n-1)   
    def lb(n) = ceil(sqrt(areaSum(n))).asInstanceOf[Int]
    
    for (n <- 28 to 30) {
      run(n,lb(n))
      run(n,lb(n)+1)
    }
  }
}
