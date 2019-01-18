case class Polynomial(list: List[Int]) {
  def + (that: Polynomial) =
    Polynomial(list.zipAll(that.list, 0, 0).map { case (x,y) => x+y })
  def * (b: Int) =
    Polynomial(list.map(b * _))
  def * (that: Polynomial): Polynomial =
    if (list.isEmpty)
      Polynomial(List.empty)
    else {
      val p = Polynomial(list.tail) * that
      that * list.head + Polynomial(0 :: p.list)
    }
}
object Polynomial {
  def apply(as: Int*) = new Polynomial(List(as: _*))
}

object Poly {
  def main(args: Array[String]) {
    println(Polynomial(1, -1, 0, 2, -3) * 2)
    println(Polynomial(1, 2, 3) + Polynomial(1, -2, 3, -4))
    println(Polynomial(1, 2, 3) * Polynomial(1, -2, 3))
  }
}
