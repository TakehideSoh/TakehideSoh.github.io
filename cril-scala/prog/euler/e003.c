#include <stdio.h>

long long e003() {
	long long n = 600851475143LL;
	long long k = 2;
	while (k*k <= n) {
		while (n % k == 0)
			n /= k;
		k++;
	}
	return n == 1 ? k : n;
}

int main(int argc, char *argv[]) {
	printf("%lld\n", e003());
}
