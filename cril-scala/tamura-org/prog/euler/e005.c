#include <stdio.h>
#include <string.h>

long long gcd(long long a, long long b) {
	if (b == 0)
		return a;
	return gcd(b, a % b);
}

long long lcm(long long a, long long b) {
	return a * b / gcd(a, b);
}

long long e005() {
	long long i;
	long long n = 1;
	for (i = 1; i <= 20; i++) {
		n = lcm(n, i);
	}
	return n;
}

int main(int argc, char *argv[]) {
	printf("%lld\n", e005());
}
