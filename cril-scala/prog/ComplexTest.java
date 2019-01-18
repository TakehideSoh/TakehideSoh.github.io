public class ComplexTest {
    public static void main(String args[]) {
        Complex z1, z2, z;
        z1 = new Complex(1.0, 2.0);
        z2 = new Complex(3.0, 4.0);
        z = z1.plus(z2).plus(5.0);
        System.out.println(z);
    }
}
