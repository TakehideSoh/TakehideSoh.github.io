package listPractice

object ListMethods {
  
  // (入力) 整数のリスト list
  // (出力) list の先頭要素を3倍にした値
  def trebleHead(list: List[Int]): Int = ???
  
  // (入力) 整数のリスト list
  // (出力) list の長さ (整数値) を3倍にした値
  def trebleLength(list: List[Int]): Int = ???
  
  // (入力) 長さが2以上の整数のリスト list
  // (出力) list の先頭の要素と最後の要素から成る長さ2のリスト
  def bothEndsOfList(list: List[Int]): List[Int] = ???
  
  // (入力) 2つの整数 n, m (但し n < m)
  // (出力) n, n+1, n+2, ... m の総和
  def sumRange(n: Int, m: Int): Int = ???
  
  // (入力) 2つの整数 n, m (但し n < m)
  // (出力) n, n+1, n+2, ... m の総和の平均
  def sumRangeAverage(n: Int, m: Int): Double = ???
  
  // (入力) 2つの整数 n, r
  // (出力) n個の中からr個を選ぶ順列
  def permutation(n: Int, r: Int): Int = ???
  
  // (入力) 空でない任意のデータ型Aのリスト list
  // (出力) list をひとつ右に回転させたリスト
  def rotateRight[A](list: List[A]): List[A] = ???
  
  // (入力) 任意のデータ型Aのリスト
  // (出力) リストが回文かどうか (回文であれば true を返す)
  def palindrome[A](list: List[A]): List[A] = ???
  
}