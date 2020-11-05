import scala.io.Source
import scala.sys.process._

case class Graph(var nodes: Set[Int] = Set.empty, var edges: Set[(Int, Int)] = Set.empty, var comments: Seq[String] = Seq.empty) {
  def edge(n1: Int, n2: Int) = if (n1 < n2) (n1, n2) else (n2, n1)
  private var adjacentMap: Map[Int, Set[Int]] = Map.empty
  private def addAdjacent(n1: Int, n2: Int) =
    adjacentMap += n1 -> (adjacentMap.getOrElse(n1, Set.empty) + n2)

  def addNode(n1: Int) = nodes += n1
  def addEdge(n1: Int, n2: Int) =
    if (n1 != n2) {
      edges += edge(n1, n2)
      addAdjacent(n1, n2)
      addAdjacent(n2, n1)
    }
  def adjacent(n: Int) = adjacentMap(n)
  def adjacentEdge(n: Int) = adjacent(n).map(n2 => edge(n, n2))
  def addComment(str: String) { 
    comments = comments ++ Seq(str)
  }

}

object Graph {
  def parse(source: Source): Graph = {
    val graph = Graph()
    val re = """e\s+(\d+)\s+(\d+)""".r
    val comment = """c\s+(.*)$""".r
    for (line <- source.getLines.map(_.trim)) {
      line match {
        case re(s1, s2) => {
//          if (s1.toInt == s2.toInt)
//            println(s"edge connected to itself ${s1.toInt} ${s2.toInt}")
          val n1 = s1.toInt; graph.addNode(n1)
          val n2 = s2.toInt; graph.addNode(n2)
//          if (graph.edges.contains((n1, n2)))
//            println(s"(${n1}, ${n2}) is appeard again.")
          graph.addEdge(n1, n2)
        }
        case comment(str) => {
          graph.addComment(str)
        }
        case _ =>
      }
    }
    graph
  }
}

def createFile(g: Graph, name: String) {
//  g.addComment("c 2014.7.16 by T.Soh: undirected edge {i, j} is written in \"e i j\" such that i<j.")

  val colName = name + ".col"
  val aspName = name + ".lp"
	val col = new java.io.PrintWriter(colName)
	val asp = new java.io.PrintWriter(aspName)

  /* generate comments */
  for (c <- g.comments) {
    col.println(s"c ${c}")
    asp.println(s"% ${c}")
  }

  /* generate #nodes #edges */
  col.println(s"p ${g.nodes.size} ${g.edges.size}")
  asp.println(s"% #nodes:${g.nodes.size} #edges:${g.edges.size}")
  asp.println(s"node(1..${g.nodes.size}).")

  /* generate edges */
  for (e <- g.edges.toSeq.sorted) {
    col.println(s"e ${e._1} ${e._2}")
    asp.println(s"edge(${e._1},${e._2}).")
  }

	col.flush
	col.close
  asp.flush
  asp.close
}

val cmd = Seq("find") :+ args(0) :+ "-name" :+ "*.col"
val files = cmd.lines

for (file <- files) {
  println(s"${file}")
  val g = Graph.parse(Source.fromFile(file))

  /* color04, tsplib, knight */
  val name = file.split('/').last.split('.').dropRight(1).mkString(".")
  /* complete */
  // val name = "complete" + "%04d".format(g.nodes.size)
  /* random */
  // val is = file.split('/').last.split('_')
  // val name = "rnd_" + "%05d".format(is(1).toInt) + "_%02d".format(is(2).toInt)
  createFile(g, name)
}

