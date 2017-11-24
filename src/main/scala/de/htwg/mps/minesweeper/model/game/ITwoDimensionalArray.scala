package de.htwg.mps.minesweeper.model.game

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

  /**
    * Get this two dimensional element as two nested immutable lists for iterating.
    *
    * @return nested lists with element
    */
  def asNestedList: List[List[A]]

  def asList: List[A]
}
