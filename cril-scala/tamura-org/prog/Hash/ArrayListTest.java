import java.util.ArrayList;

public class ArrayListTest {

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("abc");
		list.add("def");
		list.add("ghi");
		String s = list.get(0);
		System.out.println("first element is " + s);
		if (list.isEmpty()) {
			System.out.println("list is empty");
		}
		int size = list.size();
		System.out.println("list size is " + size);
	}
}
