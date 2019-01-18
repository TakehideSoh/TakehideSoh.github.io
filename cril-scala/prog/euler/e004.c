#include <stdio.h>
#include <string.h>

int palindrome(int n) {
	int i, j;
	char s[11];
	sprintf(s, "%d", n);
	for (i = 0, j = strlen(s) - 1; i <= j; i++, j--) {
		if (s[i] != s[j])
			return 0;
	}
	return 1;
}

int e004a() {
	int c = 0;
	int i, j, p;
	int m = 0;
	for (i = 100; i < 1000; i++) {
		for (j = i; j < 1000; j++) {
			c++;
			p = i * j;
			if (palindrome(p))
				m = p > m ? p : m;
		}
	}
	printf("a %d\n", c);
	return m;
}

int e004b() {
	int c = 0;
	int i, j, p;
	int m = 0;
	for (i = 100; i < 1000; i++) {
		for (j = 999; j >= i; j--) {
			c++;
			p = i * j;
			if (palindrome(p)) {
				m = p > m ? p : m;
				break;
			}
		}
	}
	printf("b %d\n", c);
	return m;
}

int e004c() {
	int c = 0;
	int i, j, p;
	int m = 1;
	for (i = 100; i < 1000; i++) {
		int j0 = m / i;
		for (j = 999; j >= i && j >= j0; j--) {
			c++;
			p = i * j;
			if (palindrome(p)) {
				m = p > m ? p : m;
				break;
			}
		}
	}
	printf("c %d\n", c);
	return m;
}

int e004d() {
	int c = 0;
	int i, j, p;
	int m = 1;
	for (i = 999; i >= 100; i--) {
		int j0 = m / i;
		for (j = 999; j >= i && j >= j0; j--) {
			c++;
			p = i * j;
			if (palindrome(p)) {
				m = p > m ? p : m;
				break;
			}
		}
	}
	printf("d %d\n", c);
	return m;
}

int e004e() {
	int c = 0;
	int i, j, p;
	int m = 1;
	for (i = 999; i >= 100; i--) {
		int j0 = m / i;
		for (j = 999; j >= i && j >= j0; j--) {
			if (i % 11 != 0 && j % 11 != 0)
				continue;
			c++;
			p = i * j;
			if (palindrome(p)) {
				m = p > m ? p : m;
				break;
			}
		}
	}
	printf("e %d\n", c);
	return m;
}

int main(int argc, char *argv[]) {
	printf("%d\n", e004a());
	printf("%d\n", e004b());
	printf("%d\n", e004c());
	printf("%d\n", e004d());
	printf("%d\n", e004e());
}
