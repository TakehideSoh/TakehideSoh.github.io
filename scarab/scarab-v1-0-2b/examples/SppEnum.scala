import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._

object SppEnum {
  def main(args: Array[String]) = {
    val csp = new CSP()
    val satSolver = new Sat4j("iterator")
    val encoder = new OrderEncoder(csp,satSolver)
    val solver = new Solver(csp,satSolver,encoder)

    val n = 15; val s = 36

    for (i <- 1 to n)  { csp.int('x(i),0,s-i) ; csp.int('y(i),0,s-i) }
    for (i <- 1 to n; j <- i+1 to n) 
      csp.add(('x(i) + i <= 'x(j)) || ('x(j) + j <= 'x(i)) || 
	      ('y(i) + i <= 'y(j)) || ('y(j) + j <= 'y(i)))
    
    var cnt = 0
    while((solver.enumerate)&&(cnt < 10)) {
      cnt = cnt + 1
      println(solver.solution.intMap)
    }
  }
}
