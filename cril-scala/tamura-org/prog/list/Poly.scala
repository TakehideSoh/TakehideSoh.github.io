object Poly {
  def add(xs: List[Int], ys: List[Int]): List[Int] =
    if (xs.isEmpty)
      ys
    else if (ys.isEmpty)
      xs
    else
      xs.head + ys.head :: add(xs.tail, ys.tail)

  def mul(b: Int, xs: List[Int]) =
    xs.map(b * _)

  def mul(xs: List[Int], ys: List[Int]): List[Int] =
    if (xs.isEmpty)
      Nil
    else
      add(mul(xs.head, ys), 0 :: mul(xs.tail, ys))

  def main(args: Array[String]) {
    println(mul(2, List(1, -1, 0, 2, -3)))
    println(add(List(1, 2, 3), List(1, -2, 3, -4)))
    println(mul(List(1, 2, 3), List(1, -2, 3)))
  }
}
