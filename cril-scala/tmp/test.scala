import scala.util.parsing.combinator._

object CalcP0 extends JavaTokenParsers {
  def expr: Parser[Any] = integer | func ~ "(" ~ expr ~ "," ~ expr ~ ")"
  def func = "+" | "-" | "*" | "/"
  def integer = wholeNumber
}
