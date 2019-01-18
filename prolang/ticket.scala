abstract class Tree {
  def isLeaf = false
  def value: Double
  def valueEquals(x: Double): Boolean = Math.abs(x - value) < 0.001
}

case class Leaf(n: Int) extends Tree {
  override def isLeaf = true
  def value: Double = n.toDouble
}

case class Node(op: String, left: Tree, right: Tree) extends Tree {
  def value = op match {
    case "+" => left.value + right.value
    case "-" => left.value - right.value
    case "*" => left.value * right.value
    case "/" => left.value / right.value
  }
}

object TestTree {
  val ops = Seq("+", "-", "*", "/")

  def genTrees(xs: Seq[Int]): Seq[Tree] = {
    if (xs.size == 1)
      Seq(Leaf(xs(0)))
    else {
      for {
        op <- ops
        i <- 1 to xs.size - 1
        (xs1, xs2) = xs.splitAt(i)
        trees1 <- TestTree.genTrees(xs1)
        trees2 <- TestTree.genTrees(xs2)
      } yield Node(op, trees1, trees2)
    }
  }
  def genTrees2(x1: Int, x2: Int): Seq[Tree] = {
    for {
      op <- ops.toList
    } yield Node(op, Leaf(x1), Leaf(x2))
  }
  def genTrees3(x1: Int, x2: Int, x3: Int) = {
    val trees1 = for {
      op <- ops
      trees <- TestTree.genTrees2(x1, x2)
    } yield Node(op, trees, Leaf(x3.toInt))
    val trees2 = for {
      op <- ops
      trees <- TestTree.genTrees2(x1, x2)
    } yield Node(op, trees, Leaf(x3.toInt))
    trees1 ++ trees2
  }
  def genTrees4(x1: Int, x2: Int, x3: Int, x4: Int) = {
    val trees1 = for {
      op <- ops
      trees <- TestTree.genTrees3(x1, x2, x3)
    } yield Node(op, trees, Leaf(x4.toInt))
    val trees2 = for {
      op <- ops
      trees <- TestTree.genTrees3(x2, x3, x4)
    } yield Node(op, Leaf(x1.toInt), trees)
    val trees3 = for {
      op <- ops
      tree1 <- TestTree.genTrees2(x1, x2)
      tree2 <- TestTree.genTrees2(x3, x4)
    } yield Node(op, tree1, tree2)
    trees1 ++ trees2 ++ trees3
  }
}
