package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.ITwoDimensionalArray

case class TwoDimensionalArray[A](rows: Int, cols: Int, vector: Vector[A]) extends ITwoDimensionalArray[A] {

  override def updated(row: Int, col: Int, element: A): TwoDimensionalArray[A] = copy(vector = vector.updated(cols * row + col, element))

  override def get(row: Int, col: Int): Option[A] = {
    if (col < 0 || col >= cols || row < 0 || row >= rows) {
      None
    } else {
      Option(vector(cols * row + col))
    }
  }

  override def foreachRow[U](f: Vector[A] => U): Unit = {
    0.until(rows).foreach(row =>
      f(vector.slice(row * cols, (row + 1) * cols))
    )
  }

  override def getCoordinates: List[(Int, Int)] =
    0.until(rows).flatMap(row =>
      0.until(cols).map(col =>
        (row, col)
      )
    )(collection.breakOut)

}

object TwoDimensionalArray {
  def apply[A](rows: Int, cols: Int, elem: A): ITwoDimensionalArray[A] =
    TwoDimensionalArray[A](rows, cols, Vector.fill(rows * cols)(elem))
}
