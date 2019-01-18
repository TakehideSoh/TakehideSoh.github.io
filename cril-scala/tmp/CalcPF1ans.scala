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
  def fint = from1to99
  def fint1word =
    "un" ^^ { _ => BigInt(1) } |
    "deux" ^^ { _ => BigInt(2) } |
    "trois" ^^ { _ => BigInt(3) } |
    "quatre-vingts" ^^ { _ => BigInt(80) } |
    "quatre-vingt" ^^ { _ => BigInt(80) } |
    "quatre" ^^ { _ => BigInt(4) } |
    "cinquante" ^^ { _ => BigInt(50) } |
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
    "vingt" ^^ { _ => BigInt(20) } |
    "trente" ^^ { _ => BigInt(30) } |
    "quarante" ^^ { _ => BigInt(40) } |
    "soixante" ^^ { _ => BigInt(60) } |
    "cent" ^^ { _ => BigInt(100) }
  def sep = "et" | "-et-" |"-"
  def cent = "cent" | "cents"
  def from1to99 = (rep(fint1word <~ sep) ~ fint1word) ^^ {
    case xs ~ x => xs.sum + x
  } | fint1word
  def fintMoreThanCent = (opt(from1to99)) <~ cent <~ 

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
