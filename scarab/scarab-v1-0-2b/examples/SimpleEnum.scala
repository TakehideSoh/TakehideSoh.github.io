import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._

object SimpleEnum {
  def main(args: Array[String]) = {
    val csp = new CSP()
    val satSolver = new Sat4j("iterator")
    // val satSolver = new Sat4j()
    val encoder = new OrderEncoder(csp,satSolver)
    val solver = new Solver(csp,satSolver,encoder)

    csp.int('x,1,3)
    csp.int('y,1,3)
    csp.add('x === 'y)

    while (solver.enumerate) {
      println(solver.solution)
    }
  }
}
