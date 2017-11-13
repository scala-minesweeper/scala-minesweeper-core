package de.htwg.mps.minesweeper.model

import scala.collection.mutable.ListBuffer

trait ITwoDimensionalArray[A] {
  def updated(row: Int, col: Int, element: A): ITwoDimensionalArray[A]
  def get(row: Int, col: Int): Option[A]
  def foreachRow[U](f: Vector[A] => U): Unit
  def getCoordinates: ListBuffer[(Int, Int)]
}
