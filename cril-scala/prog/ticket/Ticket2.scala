abstract class Tree {
  def value: Double
  def valueEquals(x: Double): Boolean = math.abs(x - value) < 1e-5
}

case class Leaf(n: Int) extends Tree {
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

