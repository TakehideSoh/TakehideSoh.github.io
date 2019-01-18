import scala.util.parsing.combinator._

object CalcP1unary extends JavaTokenParsers {
  def expr: Parser[Any] =
    integer |
    (func1 <~ "(") ~ (expr <~ ")") |
    (func2 <~ "(") ~ (expr <~ ",") ~ (expr <~ ")")
  def func1 = "-" | ident
  def func2 = "+" | "-" | "*" | "/" | ident
  def integer = wholeNumber
}
