package de.htwg.mps.minesweeper.model.grid

case class TwoDimensionalArray[A](rows: Int, cols: Int, vector: Vector[A]) extends TwoDimensional[A] {

  override def updated(row: Int, col: Int, element: A): TwoDimensionalArray[A] = copy(vector = vector.updated(cols * row + col, element))

  override def get(row: Int, col: Int): Option[A] = {
    if (col < 0 || col >= cols || row < 0 || row >= rows) {
      None
    } else {
      Option(vector(cols * row + col))
    }
  }

  override def asCoordinates: List[(Int, Int)] =
    0.until(rows).flatMap(row =>
      0.until(cols).map(col =>
        (row, col)
      )
    ).toList

  override def asNestedList: List[List[A]] = {
    0.until(rows).map(row =>
      0.until(cols).map(col =>
        vector.slice(row * cols + col, row * cols + col + 1).head
      ).toList
    ).toList
  }

  override def asList: List[A] = vector.toList

}

object TwoDimensionalArray {
  def apply[A](rows: Int, cols: Int, elem: A): TwoDimensional[A] =
    TwoDimensionalArray[A](rows, cols, Vector.fill(rows * cols)(elem))
}
