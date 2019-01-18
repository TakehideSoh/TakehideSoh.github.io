import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._
import jp.kobe_u.scarab.sapp._

val n = 15; val s = 36

for (i <- 1 to n)  { int('x(i),0,s-i) ; int('y(i),0,s-i) }
for (i <- 1 to n; j <- i+1 to n) 
  add(('x(i) + i <= 'x(j)) || ('x(j) + j <= 'x(i)) || 
      ('y(i) + i <= 'y(j)) || ('y(j) + j <= 'y(i)))

if (find) println(solution.intMap) 
