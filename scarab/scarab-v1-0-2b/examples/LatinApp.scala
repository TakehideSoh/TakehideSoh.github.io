import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._
import jp.kobe_u.scarab.sapp._

var n: Int = 13

for (i <- 1 to n; j <- 1 to n)  int('x(i,j),1,n) 
for (i <- 1 to n) add(alldiff((1 to n).map(j => 'x(i,j))))
for (i <- 1 to n) add(alldiff((1 to n).map(j => 'x(j,i))))
for (i <- 1 to n) add(alldiff((1 to n).map(j => 'x(j,(i+j-1)%n+1))))
for (i <- 1 to n)   
  add(alldiff((1 to n).map(j => 'x(j,(i+(j-1)*(n-1))%n+1))))

if (find)  println(solution.intMap)
