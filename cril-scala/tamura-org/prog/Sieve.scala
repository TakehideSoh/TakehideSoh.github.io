object Sieve {
  def sieve(xs: List[Int]): List[Int] =
    if (xs.isEmpty)
      Nil
    else
      xs.head :: sieve(xs.tail.filter(_ % xs.head != 0))

  def primes(n: Int) = sieve(Range(2, n).toList)

  def main(args: Array[String]) {
    primes(100).foreach { println }
  }
}
