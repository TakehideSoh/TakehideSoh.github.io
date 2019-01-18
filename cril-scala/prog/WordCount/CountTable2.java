import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

public class CountTable2 {
    HashMap<String,Integer> map = new HashMap<String,Integer>();

    public int get(String s) {
        int count = 0;
        if (map.containsKey(s)) {
            count = map.get(s);
        }
        return count;
    }
    
    public void add(String s) {
        int count = get(s);
        map.put(s, count + 1);
    }
    
    public void clear() {
        map.clear();
    }
    
    public int size() {
        return map.size();
    }
    
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    public List<String> getKeysByCount() {
        Set<String> set = map.keySet();
        List<String> list = new ArrayList<String>(set);
        Collections.sort(list, new Comparator<String>() {
            public int compare(String s1, String s2) {
                int count1 = get(s1);
                int count2 = get(s2);
                if (count1 != count2)
                    return count1 > count2 ? -1 : 1; 
                return s1.compareTo(s2);
            }
        });
        return list;
    }
}
