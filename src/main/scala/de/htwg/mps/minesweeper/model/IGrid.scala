package de.htwg.mps.minesweeper.model

trait IGrid {

  val bombs: Int
  def init(): IGrid
  def set(row: Int, col: Int, cell: IField): IGrid
  def get(row: Int, col: Int): Option[IField]
  def getCoordinates: List[(Int, Int)]
  def getSize: (Int, Int)
  def getFieldCount: Int
  def correctlyFlaggedBombs: Int
  def checkIfGameIsWon: Boolean
  def checkIfGameIsLost: Boolean
  def checkIfCoordinateIsBomb(col: Int, row: Int): Boolean

}
