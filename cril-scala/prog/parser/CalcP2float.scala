import scala.util.parsing.combinator._

object CalcP2float extends JavaTokenParsers {
  def expr: Parser[Double] =
    number ^^ { _.toDouble } |
    (func <~ "(") ~ (expr <~ ",") ~ (expr <~ ")") ^^ {
      case "+" ~ x ~ y => x + y
      case "-" ~ x ~ y => x - y
      case "*" ~ x ~ y => x * y
      case "/" ~ x ~ y => x / y
    }
  def func = "+" | "-" | "*" | "/"
  def number = floatingPointNumber
}
