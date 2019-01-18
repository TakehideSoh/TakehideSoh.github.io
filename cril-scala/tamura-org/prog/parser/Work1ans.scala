import scala.util.parsing.combinator._

object Work1 extends JavaTokenParsers {
  def expr: Parser[BigInt] =
    integer ^^ { BigInt(_) } |
    (func <~ "(") ~ expr ~ (rep("," ~> expr) <~ ")") ^^ {
      case "+" ~ x ~ ys => x + ys.sum
      case "-" ~ x ~ Nil => - x
      case "-" ~ x ~ ys => x - ys.sum
      case "*" ~ x ~ ys => x * ys.product
      case "/" ~ x ~ ys => x / ys.product
      case "max" ~ x ~ ys => (x +: ys).max
      case "fact" ~ x ~ Nil => (BigInt(1) to x).product
      case "gcd" ~ x ~ ys => (x +: ys).reduce(_.gcd(_))
      case "lcm" ~ x ~ ys => (x +: ys).reduce(lcm)
      case "fib" ~ x ~ Nil => fib(x.toInt)
      case "julius" ~ y ~ Seq(m,d) => BigInt(julius(y.toInt, m.toInt, d.toInt))
      case "prime" ~ x ~ Nil => BigInt(prime(x.toInt))
    }
  def func = "+" | "-" | "*" | "/" | ident
  def integer = wholeNumber

  def lcm(x: BigInt, y: BigInt) = (x * y) / x.gcd(y)
  def fib(n: Int): BigInt = {
    def fib(n: Int, f0: BigInt, f1: BigInt): BigInt =
      n match {
        case 0 => f0
        case _ => fib(n - 1, f1, f0 + f1)
      }
    fib(n, 0, 1)
  }
  def julius(year: Int, month: Int, day: Int) = {
    val y = year + ((month - 3) / 12)
    val m = (month - 3) % 12
    val d = day - 1
    val n = d + ((153*m + 2) / 5) + 365*y + (y / 4) - (y / 100) + (y / 400)
    val mjd = n - 678881
    val jdn = mjd + 2400001
    jdn
  }
  def isPrime(p: Int) = (2 to math.sqrt(p).toInt).forall(p % _ != 0)
  def nextPrime(p: Int): Int = if (isPrime(p+1)) p+1 else nextPrime(p+1)
  def prime(n: Int): Int = if (n <= 1) 2 else nextPrime(prime(n - 1))

  // Do not modify the following lines
  def test {
    for {
      line <- scala.io.Source.fromFile("test1.txt").getLines
      if ! line.matches("""\s*""")
      if ! line.matches("""\s*;.*""")
    } {
      val d = line.split("""\s*;\s*""", 2)
      val ex = d(0).trim
      val value = BigInt(d(1).trim)
      parseAll(expr, ex) match {
        case Success(v, n) if v == value =>
          println(s"OK: $ex --> $v")
        case Success(v, n) =>
          println(s"NG: $ex --> $v != $value")
        case err =>
          println(s"ERR: $ex --> $err")
      }
    }
  }
}
