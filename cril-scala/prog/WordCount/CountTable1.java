import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

public class CountTable1 extends HashMap<String,Integer> {

    public int get(String s) {
        int count = 0;
        if (containsKey(s)) {
            count = super.get(s);
        }
        return count;
    }
    
    public void add(String s) {
        int count = get(s);
        put(s, count + 1);
    }
    
    public List<String> getKeysByCount() {
        Set<String> set = keySet();
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
