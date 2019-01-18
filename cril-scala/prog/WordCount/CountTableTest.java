
public class CountTableTest {

    public static void main(String[] args) {
        CountTable1 table = new CountTable1();
        table.add("abc");
        table.add("def");
        table.add("abc");
        for (String s : table.getKeysByCount()) {
            int count = table.get(s);
            System.out.println(count + " " + s);
        }
    }

}
