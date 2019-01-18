import scala.util.parsing.combinator._

object Work2 extends JavaTokenParsers {
  def expr: Parser[BigInt] =
    integer ^^ { BigInt(_) } |
    jint |
    (func <~ "(") ~ expr ~ (rep("," ~> expr) <~ ")") ^^ {
      case "+" ~ x ~ ys => x + ys.sum
      case "-" ~ x ~ Nil => - x
      case "-" ~ x ~ ys => x - ys.sum
      case "*" ~ x ~ ys => x * ys.product
      case "/" ~ x ~ ys => x / ys.product
    }
  def func = "+" | "-" | "*" | "/" | ident
  def integer = wholeNumber
  def jint = jint3
  def jint1 =
    "一" ^^ { _ => BigInt(1) } |
    "二" ^^ { _ => BigInt(2) } |
    "三" ^^ { _ => BigInt(3) } |
    "四" ^^ { _ => BigInt(4) } |
    "五" ^^ { _ => BigInt(5) } |
    "六" ^^ { _ => BigInt(6) } |
    "七" ^^ { _ => BigInt(7) } |
    "八" ^^ { _ => BigInt(8) } |
    "九" ^^ { _ => BigInt(9) }
  def jint2 =
    (opt(jint1) <~ "十") ~ opt(jint1) ^^ {
      case None ~ None => BigInt(10)
      case Some(x1) ~ None => 10 * x1
      case None ~ Some(x2) => 10 + x2
      case Some(x1) ~ Some(x2) => 10 * x1 + x2
    } |
    jint1
  def jint3 =
    (opt(jint1) <~ "百") ~ opt(jint2) ^^ {
      case None ~ None => BigInt(100)
      case Some(x1) ~ None => 100 * x1
      case None ~ Some(x2) => 100 + x2
      case Some(x1) ~ Some(x2) => 100 * x1 + x2
    } |
    jint2

  // Do not modify the following lines
  def test {
    for {
      line <- scala.io.Source.fromFile("test2.txt").getLines
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
