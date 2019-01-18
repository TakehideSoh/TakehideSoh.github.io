

abstract class Tree {
  def value: Double
  def valueEquals(x: Double): Boolean = 
    math.abs(x - value) < 1e-5
}

case class Leaf(n: Int) extends Tree {
  def value = n.toDouble
  override def toString = n.toString
}

case class Node(op: String, 
                left: Tree, 
                right: Tree) extends Tree {

  def value = op match {
    case "+" => left.value + right.value
    case "-" => left.value - right.value
    case "*" => left.value * right.value
    case "/" => left.value / right.value
  }

  override def toString = 
    "(" + left.toString + " " + op + " " + right.toString + ")"
}

object Ticket {

  val ops = Seq("+", "-", "*", "/")

  def genTrees2(x1: Int, x2: Int): Seq[Tree] = {
    for {
      op <- ops
    } yield Node(op, Leaf(x1), Leaf(x2))
  }

  def genTrees3(x1: Int, x2: Int, x3: Int) = {
    val trees1 = for {
      op <- ops
      tree <- genTrees2(x1, x2)
    } yield Node(op, tree, Leaf(x3.toInt))
    val trees2 = for {
      op <- ops
      tree <- genTrees2(x2, x3)
    } yield Node(op, Leaf(x1.toInt), tree)
    trees1 ++ trees2
  }

  def genTrees4(x1: Int, x2: Int, x3: Int, x4: Int) = ???

}
