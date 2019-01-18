import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._

object Latin {
  def main(args: Array[String]) = {
    val csp = new CSP()
    val satSolver = new Sat4j("glucose")
    val encoder = new OrderEncoder(csp,satSolver)
    val solver = new Solver(csp,satSolver,encoder)

    def alldiff(xs: Seq[Var]) = {
      val lb = for (x <- xs) yield csp.dom(x).lb
      val ub = for (x <- xs) yield csp.dom(x).ub

      And(And(for (Seq(x, y) <- xs.combinations(2)) yield x !== y),
	  Or(for (x <- xs) yield !(x < lb.min+xs.size-1)),
	  Or(for (x <- xs) yield !(x > ub.max-xs.size+1)),
	  And(for (num <- lb.min to ub.max) yield Or(for (x <- xs) yield x === num)))
    }

    var n: Int = 5
    for (i <- 1 to n; j <- 1 to n)  csp.int('x(i,j),1,n) 
    for (i <- 1 to n) {
      csp.add(alldiff((1 to n).map(j => 'x(i,j))))
      csp.add(alldiff((1 to n).map(j => 'x(j,i))))
      csp.add(alldiff((1 to n).map(j => 'x(j,(i+j-1)%n+1))))
      csp.add(alldiff((1 to n).map(j => 'x(j,(i+(j-1)*(n-1))%n+1))))}
    
    if (solver.find)  println(solver.solution)
  }
}
