import jp.kobe_u.scarab._ , dsl._

int('a, 0, 10)
int('b, 0, 10)
int('c, 0, 10)
int('d, 0, 10)

add('a * 12 + 'b * 16 + 'c * 20 + 'd * 27 === 90)
