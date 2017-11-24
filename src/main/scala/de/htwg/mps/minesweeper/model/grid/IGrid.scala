package de.htwg.mps.minesweeper.model.grid

import de.htwg.mps.minesweeper.model.field.Field

trait IGrid {

  val bombs: Int
  def init(): IGrid
  def set(row: Int, col: Int, cell: Field): IGrid
  def get(row: Int, col: Int): Option[Field]
  def getCoordinates: List[(Int, Int)]
  def getSize: (Int, Int)
  def getFieldCount: Int
  def correctlyFlaggedBombs: Int
  def checkIfGameIsWon: Boolean
  def checkIfGameIsLost: Boolean
  def checkIfCoordinateIsBomb(col: Int, row: Int): Boolean

}
