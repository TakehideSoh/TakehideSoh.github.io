import jp.kobe_u.scarab._ , dsl._ 

def define(sum: Int) {
  reset
  boolInt('x(2))
  boolInt('x(3))
  boolInt('x(5))
  boolInt('x(8))
  boolInt('x(13))
  boolInt('x(21))
  boolInt('x(34))
  add('x(2)*2 + 'x(3)*3 + 'x(5)*5 + 'x(8)*8 + 'x(13)*13 + 'x(21)*21 + 'x(34)*34 === sum)
}
