package listPractice

object GameOfLife {
  /**
   * 氏名: 神戸 太郎
   * 学番: xxxxxxx
   * チーム: A or B (どちらか一報を残す)	
   */

  /**
   *  テスト 用
   */ 
  def test = println("okay")
  
  def inc(x: Int) = ???
  
  /**
   * show 関数 (表示，デバッグ用)
   * (入力) Seq[Seq[Int]]型の行列 matrix
   * (出力) Unit. println によって行を一行ずつ表示する.
   */
  def show(matrix: Seq[Seq[Int]]): Unit = {
    for (line <- matrix) println(???)
  } 
  
  /**
   * getNeighborsState 関数 (近傍のセルの状態を取得)
   * (入力) セルの行番号 row: Int, セルの列番号 col: Int, 行列 matrix: Seq[Seq[Int]]
   * (出力) Seq[Int]. 与えられたセルの周囲のセルの状態 (並びは任意)
   * 但し，行列の外の状態は一律に dead すなわち 0 であるものとする
   */
  def getNeighborsState(row: Int, col: Int, matrix: Seq[Seq[Int]]): Seq[Int] = {
    for (x <- -1 to 1; y <- -1 to 1 if ???) yield 
    	if (???) ??? else ???
  }
  
  /**
   * getNextCellState 関数 (次世代におけるセルの状態)
   * (入力) 現在のセルの状態 current: Int, 周囲のセルの状態 neighbors: Seq[Int]
   * (出力) Int. 次世代のセルの状態
   */
  def getNextCellState(current: Int, neighbors: Seq[Int]): Int = {
    if (???)
      if (???) 1 else 0
    else
      if (???) 1 else 0    
  }
  
  /**
   * trans 関数 (最終成果物)
   * (入力) 現在の行列 matrix: Seq[Seq[Int]]
   * (出力) Seq[Seq[Int]] 次世代の行列
   */
  def trans(matrix: Seq[Seq[Int]]): Seq[Seq[Int]] = {
    for (row <- 0 until matrix.size) yield
        for (col <- 0 until matrix.size) yield
        	getNextCellState(???, ???)
  }
    
}