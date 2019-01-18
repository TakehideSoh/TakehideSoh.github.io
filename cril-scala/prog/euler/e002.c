#include <stdio.h>

int fib(int n) {
	int f0 = 1;
	int f1 = 1;
	int i, f;
	for (i = 1; i < n; i++) {
		f = f0 + f1;
		f0 = f1;
		f1 = f;
	}
	return f1;
}

int e002() {
	int s = 0;
	int i;
	for (i = 1; ; i++) {
		int f = fib(i);
		if (f > 4000000)
			break;
		if (f % 2 == 0)
			s += f;
	}
	return s;
}

int main(int argc, char *argv[]) {
	printf("%d\n", e002());
}
