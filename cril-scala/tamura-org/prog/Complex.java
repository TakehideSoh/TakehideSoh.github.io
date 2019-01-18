public class Complex {
    public double re = 0.0;
    public double im = 0.0;

    public Complex() {
    }

    public Complex(double x) {
        re = x;
    }

    public Complex(double x, double y) {
        re = x;
        im = y;
    }

    public Complex plus(double x) {
        return new Complex(re + x, im);
    }

    public Complex plus(Complex z) {
        return new Complex(re + z.re, im + z.im);
    }

    public Complex minus(Complex z) {
        return new Complex(re - z.re, im - z.im);
    }

    public String toString() {
        return re + "+" + im + "i";
    }
}
