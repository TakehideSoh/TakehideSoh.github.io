import scala.util.parsing.combinator._

object CalcPF1 extends JavaTokenParsers {
  def expr: Parser[BigInt] = 
    integer ^^ { BigInt(_) } |
    fint |
    (func <~ "(") ~ expr ~ (rep("," ~> expr) <~ ")") ^^ {
      case "+" ~ x ~ ys => x + ys.sum
      case "-" ~ x ~ Nil => - x
      case "-" ~ x ~ ys => x - ys.sum
      case "*" ~ x ~ ys => x * ys.product
      case "/" ~ x ~ ys => x / ys.product
    }
  def func = "+" | "-" | "*" | "/" | ident
  def integer = wholeNumber
  def fint = from1to19
  def from1to16 =
    "un" ^^ { _ => BigInt(1) } |
    "deux" ^^ { _ => BigInt(2) } |
    "trois" ^^ { _ => BigInt(3) } |
    "quatre" ^^ { _ => BigInt(4) } |
    "cinq" ^^ { _ => BigInt(5) } |
    "six" ^^ { _ => BigInt(6) } |
    "sept" ^^ { _ => BigInt(7) } |
    "huit" ^^ { _ => BigInt(8) } |
    "neuf" ^^ { _ => BigInt(9) } |
    "dix" ^^ { _ => BigInt(10) } |
    "onze" ^^ { _ => BigInt(11) } |
    "douze" ^^ { _ => BigInt(12) } |
    "treize" ^^ { _ => BigInt(13) } |
    "quatorze" ^^ { _ => BigInt(14) } |
    "quinze" ^^ { _ => BigInt(15) } |
    "seize" ^^ { _ => BigInt(16) } |
  def sep = "-"
  def from1to19 = ((from1to16 <~ sep) ~ fint1word) ^^ {
    case xs ~ x => xs.sum + x
  } | fint1word

  // Do not modify the following lines
  def test {
    for {
      line <- scala.io.Source.fromFile("frenchNumbers99.txt").getLines
      if ! line.matches("""\s*""")
      if ! line.matches("""\s*;.*""")
    } {
      val d = line.split("""\s*\t\s*""", 3)
      val ex = d(1).trim
      val value = BigInt(d(0).trim)
      parseAll(expr, ex) match {
        case Success(v, n) if v == value =>
          println(s"OK: $ex --> $v")
        case Success(v, n) =>
          println(s"NG: $ex --> $v != $value")
        case err =>
          println(s"ERR: $ex --> $err")
      }
    }
  }

}
