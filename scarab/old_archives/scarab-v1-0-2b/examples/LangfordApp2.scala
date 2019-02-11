import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._
import jp.kobe_u.scarab.sapp._

val n = 4

for (i <- 1 to n) { int('l(i),1,2*n-i-1); int('r(i),1,2*n) }

for (i <- 1 to n) add('l(i) === 'r(i)-i-1)
add(alldiff((1 to n).map(i => 'l(i))))
add(alldiff((1 to n).map(i => 'r(i))))

if(find) println(solution.intMap)
