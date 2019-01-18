import java.util.HashSet;

public class HashSetTest {

	public static void main(String[] args) {
		HashSet<String> set = new HashSet<String>();
		set.add("abc");
		set.add("def");
		set.add("abc");
		if (set.contains("abc")) {
			System.out.println("set containts abc");
		}
		if (set.isEmpty()) {
			System.out.println("set is empty");
		}
		int size = set.size();
		System.out.println("set size is " + size);
	}
}
