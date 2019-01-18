object LazySieve {
  def from(n: Int): Stream[Int] =
    Stream.cons(n, from(n + 1))  

  def sieve(xs: Stream[Int]): Stream[Int] =
    Stream.cons(xs.head, sieve(xs.tail.filter(_ % xs.head != 0)))

  def primes = sieve(from(2))

  def main(args: Array[String]) {
    primes.take(10).foreach { println }
  }
}
