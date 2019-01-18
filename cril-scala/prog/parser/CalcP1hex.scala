import scala.util.parsing.combinator._

object CalcP1hex extends JavaTokenParsers {
  def expr: Parser[Any] =
    integer |
    hexnum |
    (func <~ "(") ~ (expr <~ ",") ~ (expr <~ ")")
  def func = "+" | "-" | "*" | "/"
  def integer = wholeNumber
  def hexnum = "#" ~> "[0-9a-fA-F]+".r
}
