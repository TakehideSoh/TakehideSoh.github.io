// TestTree.scala

/**
 * 氏名: 神戸 太郎
 * 学番: xxxxxxx
 * チーム: A or B (どちらか一報を残す)
 */

abstract class Tree {
  def value: Double
  def valueEquals(x: Double): Boolean = Math.abs(x - value) < 1e-5
}

case class Leaf(n: Int) extends Tree {
  def value: Double = n.toDouble
  //  override def toString = n.toString
}

case class Node(op: String, left: Tree, right: Tree) extends Tree {
  def value = op match {
    case "+" => left.value + right.value
    case "-" => ???
    case "*" => ???
    case "/" => ???
  }
  //  override def toString = 
  //    "(" + left.toString + " " + op + " " + right.toString + ")"
}

object TestTree {
  val ops = Seq("+", "-", "*", "/")

  /**
   * genTrees2 関数
   */
  def genTrees2(x1: Int, x2: Int): Seq[Tree] = {
    for {
      op <- ops.toList
    } yield Node(op, Leaf(x1), Leaf(x2))
  }

  /**
   * genTrees3 関数
   */
  def genTrees3(x1: Int, x2: Int, x3: Int) = {
    val trees1 = for {
      op <- ops
      trees <- TestTree.genTrees2(x1, x2)
    } yield Node(op, trees, Leaf(x3))
    val trees2 = for {
      op <- ops
      trees <- TestTree.genTrees2(x2, x3)
    } yield Node(op, Leaf(x1), trees)
    trees1 ++ trees2
  }

  /**
   * 課題 genTrees4
   * (入力) 整数4つ. Int型の x1, x2, x3, x4
   * (出力) 与えられた4つの整数列 (順序固定) から生成できる全てのTreeの列. Seq[Tree]型.
   * (実装の条件) genTrees2, genTrees3 を呼び出す
   * ヒント:
   */
  def genTrees4(x1: Int, x2: Int, x3: Int, x4: Int): Seq[Tree] = ???
  // http://kix.istc.kobe-u.ac.jp/~soh/prolang/Ticket.html の内容をまずコピーしよう

  /**
   * 課題 findSolutions
   * (入力) Seq[Int]型のxs (長さは4で固定). Int型の a. (すなわち切符問題の入力)
   * (出力) 与えられた4つの整数列 (順序固定) から値が a となる全てのTreeの列. Seq[Tree]型.
   * (実装の条件) genTrees4 を呼び出す
   * 関数の外枠 (??? を埋めると関数が完成する)
   * ヒント: http://kix.istc.kobe-u.ac.jp/~soh/prolang/Ticket.html 参照
   */
  def findSolutions(xs: Seq[Int], a: Int): Seq[Tree] = ???

  /**
   * 課題 findAll
   * (入力) 整数4つからなる列 Seq[Int]型の xs. 求めたい値 a.
   * (出力) 与えられた4つの整数列 (順序の入れ替えを許す) から生成できる全てのTreeの列のうち値がaと一致するもの. Seq[Tree]型.
   * (実装の条件) genTrees4 を呼び出す
   * 関数の外枠 (??? を埋めると関数が完成する. )
   * ヒント: http://kix.istc.kobe-u.ac.jp/~soh/prolang/Ticket.html 参照
   */
  def findAll(xs: Seq[Int], a: Int): Seq[Tree] = ???
  // http://kix.istc.kobe-u.ac.jp/~soh/prolang/Ticket.html の内容をまずコピーしよう    

  /**
   * チャレンジ課題
   * (入力) 整数の列 (順序固定，長さ任意)．Seq[Int]型.
   * (出力) 与えられた整数列 (順序固定，長さ任意) から生成できる全てのTreeの列. Seq[Tree]型.
   * (実装の条件) 再帰プログラミングを使う．すなわち自分自身の中で genTrees を呼び出す．
   */
  def genTrees(xs: Seq[Int]): Seq[Tree] = ???

  /**
   * チャレンジ課題
   * (入力) なし
   * (出力) 答えが一つしか存在しないような切符問題 (整数4つで順序固定) を全て．Seq[(Seq[Int], Int)]型．
   * (実装の条件) 再帰プログラミングを使う．すなわち自分自身の中で genTrees を呼び出す．
   */
  def genDifficult: Seq[(Seq[Int], Int)] = ???

}