import scala.util.parsing.combinator._

object CalcP4bigint extends JavaTokenParsers {
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
}
