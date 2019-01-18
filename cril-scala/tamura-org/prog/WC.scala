object WC {
  def main(args: Array[String]) {
    var map: Map[String,Int] = Map.empty
    for {
      line <- scala.io.Source.stdin.getLines
      word <- line.split("[^a-zA-Z]+").map(_.toUpperCase)
      if word != ""
    } {
      map += word -> (map.getOrElse(word, 0) + 1)
    }
    val sortedWords = map.keySet.toSeq.sortWith(
      (w1,w2) => map(w1) > map(w2) || (map(w1) == map(w2) && w1 < w2)
    )
    for (word <- sortedWords) {
      println(s"${map(word)}\t$word")
    }
  }
}

class CountTable {
  private var map: Map[String,Int] = Map.empty
  def apply(s: String) = map(s)
  def add(s: String) { map += s -> (map.getOrElse(s,0) + 1) }
  def clear { map = Map.empty }
  def size = map.size
  def isEmpty = map.isEmpty
  def getKeysByCount = map.keySet.toSeq.sortWith(
    (s1,s2) => map(s1) > map(s2) || (map(s1) == map(s2) && s1 < s2)
  )
}

object WC2 {
  def main(args: Array[String]) {
    var table = new CountTable()
    for {
      line <- scala.io.Source.stdin.getLines
      word <- line.split("[^a-zA-Z]+")
      if word != ""
    } {
      table.add(word.toUpperCase)
    }
    for (word <- table.getKeysByCount) {
      println(s"${table(word)}\t$word")
    }
  }
}
