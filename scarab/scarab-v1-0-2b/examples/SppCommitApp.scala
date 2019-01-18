import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._
import jp.kobe_u.scarab.sapp._

val n = 5
var ub = 15
var lb = 5

for (i <- 1 to n) { int('x(i),0,ub-i); int('y(i),0,ub-i) }
for (i <- 1 to n; j <- i+1 to n)
  add(('x(i) + i <= 'x(j)) || ('x(j) + j <= 'x(i)) || ('y(i) + i <= 'y(j)) || ('y(j) + j <= 'y(i)))

commit

while(lb < ub) {
  var size = (lb + ub) / 2
  for (i <- 1 to n) { add('x(i) + i <= size); add('y(i) + i <= size) }
  if (find) {
    ub = size    
    commit
  } else {
    lb = size + 1
    rollback
  }
}
println(lb + " is optimum value")

var cnt = 0
while((find)&&(cnt < 10)) {
  cnt = cnt + 1
  println(solution.intMap)
}
println(cnt + " optimum solutions found")
