package de.htwg.mps.minesweeper.model

trait ITwoDimensionalArray[A] {
  val cols: Int
  val rows: Int

  def updated(row: Int, col: Int, element: A): ITwoDimensionalArray[A]

  def get(row: Int, col: Int): Option[A]

  /**
    * Get all coordinates for the elements in this array as pair (row, column).
    *
    * @return list of coordinates (row, column)
    */
  def asCoordinates: List[(Int, Int)]

  def asNestedList: List[List[A]]
}
