import scala.util.parsing.combinator._

object CalcP2 extends JavaTokenParsers {
  def expr: Parser[Int] =
    integer ^^ { _.toInt } |
    (func <~ "(") ~ (expr <~ ",") ~ (expr <~ ")") ^^ {
      case "+" ~ x ~ y => x + y
      case "-" ~ x ~ y => x - y
      case "*" ~ x ~ y => x * y
      case "/" ~ x ~ y => x / y
    }
  def func = "+" | "-" | "*" | "/"
  def integer = wholeNumber
}
