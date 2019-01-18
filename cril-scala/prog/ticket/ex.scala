
abstract class Top
case class TA(n:Int, b: Boolean) extends Top
case class TB(n:Int, b: Boolean) extends Top

val seq = 
  Seq(
    TB(1,false),
    TA(2,false),
    TB(3,false),
    TA(4,true),
    TB(5,true),
    TB(1,false),
    TA(2,true),
    TB(3,false),
    TA(4,true),
    TA(5,true))

val ff1 = for {
  s <- seq
  f = s match {
    case t: TA if t.b => Some(t.n)
    case t: TB if !t.b => Some(t.n)
    case _ => None
  }
} yield f



val ff2 = for {
  s <- seq
  f = s match {
    case TA(n0,true) => Some(n0)
    case TB(n0,false) => Some(n0)
    case _ => None
  }
} yield f








