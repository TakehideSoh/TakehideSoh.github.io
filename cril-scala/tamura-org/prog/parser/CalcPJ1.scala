import scala.util.parsing.combinator._

object CalcPJ1 extends JavaTokenParsers {
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
  def jint = jint1
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
}
