import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._
import jp.kobe_u.scarab.sapp._

val n = 5
var ub = 15
var lb = 5

for (i <- 1 to n) { int('x(i),0,ub-i); int('y(i),0,ub-i) }
for (i <- 1 to n; j <- i+1 to n)
  add(('x(i) + i <= 'x(j)) || ('x(j) + j <= 'x(i)) || ('y(i) + i <= 'y(j)) || ('y(j) + j <= 'y(i)))

int('m, 0, ub)
for (i <- 1 to n) { add('x(i) + i <= 'm); add('y(i) + i <= 'm) }

while(lb < ub) {
  var size = (lb + ub) / 2
  if (find('m <= size))
    ub = size
  else
    lb = size + 1
}
println(lb + " is optimum value")

var cnt = 0; add('m <= lb)
while((find)&&(cnt < 10)) {
  cnt = cnt + 1
  println(solution.intMap)
}
println(cnt + " optimum solutions found")
