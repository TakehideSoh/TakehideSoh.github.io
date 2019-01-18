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
      case "fact" ~ x ~ Nil => (BigInt(1) to x).product
    }
  def func = "+" | "-" | "*" | "/" | ident
  def integer = wholeNumber

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
