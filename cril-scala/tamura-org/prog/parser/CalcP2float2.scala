import scala.util.parsing.combinator._

object CalcP2float2 extends JavaTokenParsers {
  def expr: Parser[Double] =
    number ^^ { _.toDouble } |
    (func1 <~ "(") ~ (expr <~ ")") ^^ {
      case "-" ~ x => - x
      case "abs" ~ x => math.abs(x)
    } |
    (func2 <~ "(") ~ (expr <~ ",") ~ (expr <~ ")") ^^ {
      case "+" ~ x ~ y => x + y
      case "-" ~ x ~ y => x - y
      case "*" ~ x ~ y => x * y
      case "/" ~ x ~ y => x / y
      case "max" ~ x ~ y => math.max(x, y)
    }
  def func1 = "-" | ident
  def func2 = "+" | "-" | "*" | "/" | ident
  def number = floatingPointNumber
}
