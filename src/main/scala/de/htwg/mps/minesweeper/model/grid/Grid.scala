package de.htwg.mps.minesweeper.model.grid

import de.htwg.mps.minesweeper.model.field.Field

trait Grid {

  val bombs: Int
  def init(): Grid
  def set(row: Int, col: Int, cell: Field): Grid
  def get(row: Int, col: Int): Option[Field]
  def getCoordinates: List[(Int, Int)]
  def getSize: (Int, Int)
  def getFieldCount: Int
  def fields: List[Field]

}
