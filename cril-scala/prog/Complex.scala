case class Complex(re: Double = 0.0, im: Double = 0.0) {
  def negate =
    Complex(- re, - im)
  def plus(that: Complex) =
    Complex(re + that.re, im + that.im)
  def minus(that: Complex) =
    Complex(re - that.re, im - that.im)
  def times(that: Complex) =
    Complex(re*that.re - im*that.im, re*that.im + im*that.re)
  def unary_- = negate
  def + (that: Complex) = plus(that)
  def - (that: Complex) = minus(that)
  def * (that: Complex) = times(that)
  def plus(x: Double) = Complex(re + x, im)
  def + (x: Double) = plus(x)
}
object ComplexConversion {
  implicit def double2complex(x: Double): Complex = Complex(x)
}
