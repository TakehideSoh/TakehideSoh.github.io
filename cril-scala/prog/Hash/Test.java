import java.util.HashMap;

public class Test {
	
	static void countUp(String word, HashMap<String,Integer> map) {
		int count = 0;
		if (map.containsKey(word)) {
			count = map.get(word);
		}
		map.put(word, count + 1);
	}

	public static void main(String[] args) {
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		countUp("abc", map);
		countUp("def", map);
		countUp("def", map);
		countUp("abc", map);
		countUp("def", map);
		for (String key : map.keySet()) {
			System.out.println(key + " : " + map.get(key));
		}
	}

}
