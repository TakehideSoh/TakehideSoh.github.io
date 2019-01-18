import scala.math.{sqrt, ceil}
import java.lang.management.ThreadMXBean
import java.lang.management.ManagementFactory
import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._

object SppBench {
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

    for (i <- N-5 to N; j <- i+1 to N) {
      val sum = i + j - 1
//      csp.add(!(And(('x(i) + i <= sum),('x(j) + j <= sum),h(i,j))))
//      csp.add(!(And(('y(i) + i <= sum),('y(j) + j <= sum),v(i,j))))
      csp.add(!(And(('x(i) >= L - sum),('x(j) >= L - sum),h(i,j))))
      csp.add(!(And(('y(i) >= L - sum),('y(j) >= L - sum),v(i,j))))
    }

    var res: Boolean = false
    try {
      res = solver.find
    } catch {
      case _: org.sat4j.specs.TimeoutException => {
	return Seq(satSolver.nVars.toString,satSolver.nConstraints.toString,"T.O.")
      }
    }
    // Solve CSP
    if (res)
      Seq(satSolver.nVars.toString,satSolver.nConstraints.toString,"SAT")
    else
      Seq(satSolver.nVars.toString,satSolver.nConstraints.toString,"UNSAT")
  }

  def main(args: Array[String]) {

    def diff(t2: Long, t1: Long): Double = (t2 - t1) / scala.math.pow(10,9)

    def inAreaSize(N: Int): Int = (1 to N).foldLeft(0){(acc, i) => acc + i*i}

    val threadMXBean: ThreadMXBean = ManagementFactory.getThreadMXBean()

    def run(n: Int, l: Int, f: Double) {
      val time11 = threadMXBean.getCurrentThreadCpuTime()
      val info = solveSpp(n,l)
      val time12 = threadMXBean.getCurrentThreadCpuTime()
      println(Seq(n,l).mkString(",") + "," + f + "," + info.mkString(",") + "," + diff(time12,time11))
    }

    def sortFillingRate(a: Int, b: Int): Seq[(Double, Int, Int)] = {
      var acc: Seq[(Double, Int, Int)] = Seq.empty
      for (i <- a to b) {
	val area: Double = inAreaSize(i)
	val ln: Double = ceil(sqrt(area)).asInstanceOf[Int]
	acc = acc :+ (area/(ln*ln),i,ln.toInt)
	acc = acc :+ (area/((ln+1)*(ln+1)),i,(ln+1).toInt)
      }
      acc.sortWith((a, b) => a._1 < b._1)
    }
    
    val nlList = sortFillingRate(args(0).toInt,args(1).toInt)
    for (i <- nlList) 
      if(i._1 > 0.96) run(i._2,i._3,i._1)
  }
}

