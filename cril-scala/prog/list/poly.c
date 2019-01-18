#include <stdio.h>
#include <stdlib.h>
  
typedef struct node_struct NODE;
struct node_struct {
	int elem;
	NODE *next;
};
  
NODE *new_node(int elem, NODE *next) {
	NODE *p;
	p = (NODE*)malloc(sizeof(NODE));
	p->elem = elem;
	p->next = next;
	return p;
}
  
NODE *imul(int b, NODE *p) {
	if (p == NULL) {
		return NULL;
	} else {
		return new_node(b * p->elem, imul(b, p->next));
	}
}
  
NODE *add(NODE *p, NODE *q) {
	if (p == NULL) {
		return q;
	} else if (q == NULL) {
		return p;
	} else {
		return new_node(p->elem + q->elem, 
						add(p->next, q->next));
	}
}
  
NODE *mul(NODE *p, NODE *q) {
	if (p == NULL) {
		return NULL;
	} else {
		NODE *r = mul(p->next, q);
		return add(imul(p->elem, q), new_node(0, r));
	}
}
  
void print(NODE *p) {
	for (; p != NULL; p = p->next) {
		printf("%d ", p->elem);
	}
	printf("\n");
}
  
int main(int argc, char *argv[]) {
	NODE *p, *q, *r;
	p = new_node(1, NULL);
	p = new_node(2, p);
	p = new_node(3, p);
	print(p);
	p = new_node(1, new_node(-1, new_node(0, new_node(2, new_node(-3, NULL)))));
	q = imul(2, p);
	print(q);
	p = new_node(1, new_node(2, new_node(3, NULL)));
	q = new_node(1, new_node(-2, new_node(3, new_node(-4, NULL))));
	r = add(p, q);
	print(r);
	p = new_node(1, new_node(2, new_node(3, NULL)));
	q = new_node(1, new_node(-2, new_node(3, NULL)));
	r = mul(p, q);
	print(r);
	return 0;
}
