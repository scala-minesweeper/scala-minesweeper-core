package de.htwg.mps.minesweeper.model

trait ITwoDimensionalArray[A] {
  def updated(row: Int, col: Int, element: A): ITwoDimensionalArray[A]

  def get(row: Int, col: Int): Option[A]

  def detectedNonBombFields : Int

  def foreachRow[U](f: Vector[A] => U): Unit
  val cols: Int
  val rows: Int

  /**
    * Get all coordinates for the elements in this array as pair (row, column).
    *
    * @return list of coordinates (row, column)
    */
  def getCoordinates: List[(Int, Int)]
}
