import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._
import jp.kobe_u.scarab.sapp._

val n = 4

for (i <- 1 to 2*n) int('x(i),1,n)
for (i <- 1 to n) 
  add(Or(for (j <- 1 to 2*n-i-1) yield And(('x(j) === 'x(j+i+1)), ('x(j) === i))))

if(find) println(solution.intMap)
