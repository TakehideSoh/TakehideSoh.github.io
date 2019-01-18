abstract class Tree {
  def value: Double
}

case class Leaf(n: Int) extends Tree {
  def value: Double = n.toDouble
}

case class Node(op: String, left: Tree, right: Tree) extends Tree {
  def value = op match {
    case "+" => left.value + right.value
    case "-" => ???
    case "*" => ???
    case "/" => ???
  }
}

