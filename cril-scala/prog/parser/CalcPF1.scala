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
      case err => throw new Exception(s"Error in matching to parse $err")
    }
  def func = "+" | "-" | "*" | "/" | ident
  def integer = wholeNumber
  def fint = fintMoreThan2word
  def fint1word =
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
    "vingt" ^^ { _ => BigInt(20) } |
    "trente" ^^ { _ => BigInt(30) } |
    "quarante" ^^ { _ => BigInt(40) } |
    "cinquante" ^^ { _ => BigInt(50) } |
    "soixante" ^^ { _ => BigInt(60) } |
    "cent" ^^ { _ => BigInt(100) } |
  def sep = "-" | " "
  def fintMoreThan2word = rep(fint1word <~ sep) ~ fint1word | fint1word

  // Do not modify the following lines
  var verbose = true
  def test:Unit = test(1L,999999999999L)
  def test(ub: Long):Unit = test(1,ub)
  def test(lb: Long, ub: Long):Unit = {
    for {
      line <- scala.io.Source.fromFile("frenchNumbersBillion.txt").getLines
      if ! line.matches("""\s*""")
      if ! line.matches("""\s*;.*""")
      d = line.split("""\s*\t\s*""", 3)
      ex = d(1).trim
      value = BigInt(d(0).trim)
      if BigInt(lb) <= value && value <= BigInt(ub)
    } {
      parseAll(expr, ex) match {
        case Success(v, n) if v == value =>
          if (verbose) println(s"OK: $ex --> $v")
        case Success(v, n) =>
          println(s"NG: $ex --> $v != $value")
        case err =>
          println(s"ERR: $ex --> $err")
      }
    }
  }

}
