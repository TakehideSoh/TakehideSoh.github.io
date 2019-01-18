import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._
import jp.kobe_u.scarab.sapp._

val nodes = Seq(1,2,3,4,5)
val edges = Seq((1,2),(1,5),(2,3),(2,4),(3,4),(4,5))
var maxColor = 4;

int('color,1,maxColor)
for (i <- nodes) int('n(i),1,maxColor)
for (i <- nodes) add('n(i) <= 'color)
for ((i,j) <- edges)  add('n(i) !== 'n(j))

while (find('color <= maxColor)) {
  println(solution)
  maxColor -= 1
}
