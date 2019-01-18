import scala.util.parsing.combinator._

object CalcPJ3 extends JavaTokenParsers {
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
}
