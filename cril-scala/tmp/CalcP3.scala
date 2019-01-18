import scala.util.parsing.combinator._

object CalcP3 extends JavaTokenParsers {
  def expr: Parser[Any] =
    integer |
    (func <~ "(") ~ expr ~ (rep("," ~> expr) <~ ")")
  def func = "+" | "-" | "*" | "/" | ident
  def integer = wholeNumber
}
