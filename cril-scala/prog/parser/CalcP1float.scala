import scala.util.parsing.combinator._

object CalcP1float extends JavaTokenParsers {
  def expr: Parser[Any] = number | (func <~ "(") ~ (expr <~ ",") ~ (expr <~ ")")
  def func = "+" | "-" | "*" | "/"
  def number = floatingPointNumber
}
