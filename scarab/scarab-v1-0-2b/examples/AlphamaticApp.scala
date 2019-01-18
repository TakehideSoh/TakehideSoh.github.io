import jp.kobe_u.scarab.csp._
import jp.kobe_u.scarab.solver._
import jp.kobe_u.scarab.sapp._

// SAT + IS + FUN = TRUE

use(new Sat4jDimacs)

val base: Int = 10
// S, I F and T are not zero
val s = int('s,1,base-1)
val i = int('i,1,base-1)
val f = int('f,1,base-1)
val t = int('t,1,base-1)
val a = int('a,0,base-1)
val u = int('u,0,base-1)
val n = int('n,0,base-1)
val r = int('r,0,base-1)
val e = int('e,0,base-1)

// A constraint model for the problem "SAT + IS + FUN = TRUE"
// add(
// s*base*base + a*base + t + 
//               i*base + s +
// f*base*base + u*base + n 
// === 
// t*base*base*base + r*base*base + u*base + e)

// The following computes a solution faster;)
val c1 = int('c1, 0, 2)
val c2 = int('c2, 0, 2)
val c3 = int('c3, 0, 2)
add(t + s + n      === e + c1*base)
add(a + i + u + c1 === u + c2*base)
add(s +     f + c2 === r + c3*base)
add(            c3 === t)

add(alldiff(List(s,i,f,t,a,u,n,r,e)))

// if (find)  println(solution.intMap)

outDimacs("test.cnf")
