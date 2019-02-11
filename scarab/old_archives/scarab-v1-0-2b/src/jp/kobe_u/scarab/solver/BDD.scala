package jp.kobe_u.scarab.solver

import jp.kobe_u.scarab._

abstract class BDD {
  def and(that: BDD): BDD = {
    def andF(left: BDD, right: BDD): BDD = (left, right) match {
      case (False, _) => False
      case (_, False) => False
      case (True, _) => right
      case (_, True) => left
    }
    this.op(andF, that)
  }
  def or(that: BDD): BDD = {
    def orF(left: BDD, right: BDD): BDD = (left, right) match {
      case (False, _) => right
      case (_, False) => left
      case (True, _) => True
      case (_, True) => True
    }
    this.op(orF, that)
  }
  def op(f: (BDD,BDD) => BDD, that: BDD): BDD = (this, that) match {
    case (False, _) => f(False, that)
    case (_, False) => f(this, False)
    case (True, _) => f(True, that)
    case (_, True) => f(this, True)
    case (node1: Node, node2: Node) => {
      if (node1 == node2)
    	node1
      else if (node1.x == node2.x)
        Node.get(node1.x, node1.left.op(f, node2.left), node1.right.op(f, node2.right))
      else if (node1.x < node2.x) 
        Node.get(node1.x, node1.left.op(f, node2), node1.right.op(f, node2))
      else
        Node.get(node2.x, node2.left.op(f, node1), node2.right.op(f, node1))
    }
  }
  def not: BDD = this match {
    case False => True
    case True => False
    case node: Node => {
      Node.get(node.x, node.left.not, node.right.not)
    }
  }
}

object BDD {
  var showHash = Set("False","True")
  var rankHash: scala.collection.mutable.Map[Int,Seq[Int]] = 
    scala.collection.mutable.Map.empty[Int,Seq[Int]]

  import scala.sys.process._

  def rm(str: String) = Seq("rm",str).!
  def dot(bdd: BDD): Unit = {
    val tmp = "/tmp/bdd" + bdd.hashCode.toString
    val dotFile = tmp + ".dot"
    val gifFile = tmp + ".gif"
    dot(bdd, dotFile)
    Seq("sleep","1").!
    rm(dotFile)
    rm(gifFile)
  }

  def dot(bdd: BDD, dot: String): Unit = {
    import java.io.PrintWriter
    showHash = Set.empty
    val gifFile = dot.split('.').head + ".gif"
    val out = new PrintWriter(dot)
    out.println("digraph sample {")
    show(bdd,out)
    rankHash.foreach( i => out.println("{rank = same; " + i._2.mkString("; ") + " }"))
    out.println("}")
    out.close

    Seq("dot", "-Tgif", dot, "-o", gifFile).!
    Seq("open", gifFile).!
  }

  def show(bdd :BDD, out: java.io.PrintWriter): String = bdd match {
    case False => {
      out.println("False [shape = box, width = 0.5, height = 0.5, label = \"F\"];")
      "False"
    }
    case True => {
      out.println("True [shape = box, width = 0.5, height = 0.5, label = \"T\"];")
      "True"
    }
    case node: Node => {
      val n1 = show(node.left, out)
      val n2 = show(node.right, out)
      val n = node.hashCode.toString
      if (!showHash.contains(n)) {
	out.println(n + " [shape = circle, label = \"" + node.x + "\"];")
	if (!rankHash.contains(node.x))
	  rankHash(node.x) = Seq(n.toInt)
	else
	  rankHash(node.x) = rankHash(node.x) :+ n.toInt
	showHash = showHash + n
      }
      out.println(n + " -> " + n1 + "[style = dashed];")
      out.println(n + " -> " + n2)
      n.toString
    }
  }

  val a = Node(1,False,True)
  val b = Node(2,False,True)
  val not_a = Node(1,True,False)
  val not_b = Node(2,False,True)
  val f1 = a.and(b)
  val f2 = not_a.and(not_b)


}

object True extends BDD {
  override def toString = "True"
}

object False extends BDD {
  override def toString = "False"
}

case class Node(x: Int, left: BDD, right: BDD) extends BDD

object Node extends BDD {
  import scala.collection.mutable.HashMap
  var nodeHash = new HashMap[(Int, Int, Int), BDD]
  def get(x: Int, left: BDD, right: BDD): BDD = {
     if (left == right)
      left
    else {
      val lh = left.hashCode
      val rh = right.hashCode
      if (nodeHash.contains((x,lh,rh))) 
	nodeHash((x,lh,rh))
      else {
        val newBDD = new Node(x, left, right)
        nodeHash += ((x, lh, rh) -> newBDD)
        newBDD
      }
    }
  }
}

