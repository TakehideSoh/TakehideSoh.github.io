public class AccTest {
    public static void main(String args[]) {
        Accumulator acc1;
        Accumulator acc2;
        acc1 = new Accumulator();
        acc2 = new Accumulator();
        acc1.add(10);
        acc2.add(20);
        acc1.add(30);
        acc2.add(40);
        System.out.println(acc1.value());
        System.out.println(acc2.value());
    }
}

class Accumulator {
    int accumulator = 0;

    void add(int x) {
        accumulator += x;
    }

    int value() {
        return accumulator;
    }
}
