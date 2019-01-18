import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
  
public class MyList {
    private List<Integer> as;
  
    public MyList() {
		as = new ArrayList<Integer>();
    }
  
    public MyList(List<Integer> as) {
		this.as = new ArrayList<Integer>(as);
    }
  
    public boolean isEmpty() {
		return as.isEmpty();
    }
  
    public int head() {
		return as.get(0);
    }
  
    public MyList tail() {
		return new MyList(as.subList(1, as.size()));
    }
  
    public MyList cons(int a) {
		List<Integer> as1 = new ArrayList<Integer>(as);
		as1.add(0, a);
		return new MyList(as1);
    }
  
    public MyList mul(int b) {
		if (isEmpty())
			return this;
		return tail().mul(b).cons(b * head());
    }
  
    public MyList add(MyList ys) {
		if (isEmpty())
			return ys;
		else if (ys.isEmpty())
			return this;
		return tail().add(ys.tail()).cons(head() + ys.head());
    }
  
    public MyList mul(MyList ys) {
		if (isEmpty())
			return this;
		return ys.mul(head()).add(tail().mul(ys).cons(0));
    }
  
    public String toString() {
		String s = "";
		String delim = "";
		for (int a : as) {
			s += delim + a;
			delim = ", ";
		}
		return "(" + s + ")";
    }
  
    public static void main(String[] args) {
		MyList p, q, r;
		p = new MyList(Arrays.asList(1, -1, 0, 2, -3));
		System.out.println(p.mul(2));
		p = new MyList(Arrays.asList(1, 2, 3));
		q = new MyList(Arrays.asList(1, -2, 3, -4));
		System.out.println(p.add(q));
		p = new MyList(Arrays.asList(1, 2, 3));
		q = new MyList(Arrays.asList(1, -2, 3));
		System.out.println(p.mul(q));
    }
}
