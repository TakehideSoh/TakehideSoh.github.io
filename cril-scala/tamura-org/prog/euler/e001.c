#include <stdio.h>

int e001a() {
	int s = 0;
	int n;
	for (n = 1; n < 1000; n++) {
		if (n % 3 == 0 || n % 5 == 0)
			s += n;
	}
	return s;
}

int msum(int p, int lim) {
	int m = (lim - 1) / p;
	return p * m * (m + 1) / 2;
}

int e001b() {
	return msum(3, 1000) + msum(5, 1000) - msum(15, 1000);
}

int main(int argc, char *argv[]) {
	printf("%d\n", e001a());
	printf("%d\n", e001b());
}
