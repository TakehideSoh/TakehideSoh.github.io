import java.util.HashMap;

public class HashMapTest {

	public static void main(String[] args) {
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("abc", 1);
		map.put("def", 1);
		map.put("abc", 2);
		if (map.containsKey("abc")) {
			int x = map.get("abc");
			System.out.println("map contains " + x + " for abc");
		}
		if (map.isEmpty()) {
			System.out.println("map is empty");
		}
		int size = map.size();
		System.out.println("map size is " + size);
	}

}
