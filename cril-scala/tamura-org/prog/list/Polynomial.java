import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Polynomial {
	private List<Integer> as = null;

	public Polynomial() {
		as = new ArrayList<Integer>();
	}

	public Polynomial(List<Integer> as) {
		this.as = as;
	}

	public Polynomial add(Polynomial p) {
		Polynomial r = new Polynomial();
		for (int i = 0; i < as.size() || i < p.as.size(); i++) {
			int a = 0;
			if (i < as.size())
				a += as.get(i);
			if (i < p.as.size())
				a += p.as.get(i);
			r.as.add(a);
		}
		return r;
	}

	public Polynomial xmul(int n) {
		List<Integer> bs = new ArrayList<Integer>(as);
		for (int i = 0; i < n; i++)
			bs.add(0, 0);
		return new Polynomial(bs);
	}

	public Polynomial mul(int b) {
		Polynomial r = new Polynomial();
		for (int a : as) {
			r.as.add(b * a);
		}
		return r;
	}

	public Polynomial mul(Polynomial p) {
		Polynomial r = new Polynomial();
		for (int i = 0; i < as.size(); i++) {
			Polynomial q = p.mul(as.get(i));
			r = r.add(q.xmul(i));
		}
		return r;
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
		Polynomial p, q, r;
		p = new Polynomial(Arrays.asList(1, -1, 0, 2, -3));
		System.out.println(p.mul(2));
		p = new Polynomial(Arrays.asList(1, 2, 3));
		q = new Polynomial(Arrays.asList(1, -2, 3, -4));
		System.out.println(p.add(q));
		p = new Polynomial(Arrays.asList(1, 2, 3));
		q = new Polynomial(Arrays.asList(1, -2, 3));
		System.out.println(p.mul(q));
	}
}
