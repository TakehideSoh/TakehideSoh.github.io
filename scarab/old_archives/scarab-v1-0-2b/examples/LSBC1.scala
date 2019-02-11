import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._
import jp.kobe_u.scarab.sapp._

var n: Int = args(0).toInt

use(new Sat4j("glucose"))
use(new CardOrderEncoder(csp, satSolver))

for (i <- 1 to n; j <- 1 to n; num <- 1 to n)  
  int('x(i,j,num),0,1) 

def BC1(xs: Seq[Var]): Term = 
  Sum(xs)

for (num <- 1 to n) {
  for (i <- 1 to n) add(BC1((1 to n).map(j => 'x(i,j,num)))===1)
  for (i <- 1 to n) add(BC1((1 to n).map(j => 'x(j,i,num)))===1)
  for (i <- 1 to n) add(BC1((1 to n).map(j => 'x(j,(i+j-1)%n+1,num))) === 1)
  for (i <- 1 to n) add(BC1((1 to n).map(j => 'x(j,(i+(j-1)*(n-1))%n+1,num))) === 1)
}

for (i <- 1 to n; j <- 1 to n)
  add(BC1((1 to n).map(k => 'x(i,j,k))) === 1)

if (find) {
  for (i <- 1 to n) {
    var seq: Seq[Int] = Seq.empty
    for (j <- 1 to n) {
      (1 to n).find(k => encoder.decode('x(i,j,k)) == 1) match {
	case None => seq = seq :+ 0
	case Some(num) => seq = seq :+ num
      }
    }
    println(seq.mkString(" "))
  }      
}

val out
satSolver.getStat.foreach(i => println("LS(" + n + "): " + i))
