import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._
import jp.kobe_u.scarab.sapp._

use(new CardOrderEncoder(csp, satSolver))

int('x,0,1)
int('y,0,1)
int('z,0,1)

add('x + 'y + 'z === 1)

val out = new java.io.PrintWriter("Test.log")
val prefix = "Test"

find

satSolver.getStat.foreach(i => out.println(prefix + " " + i))
satSolver.printInfos(out, prefix)
out.close
out.println(solution.intMap)
