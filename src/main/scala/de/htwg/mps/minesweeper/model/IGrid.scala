package de.htwg.mps.minesweeper.model

trait IGrid {

  def init(): IGrid
  def set(row: Int, col: Int, cell: IField): IGrid
  def get(row: Int, col: Int): Option[IField]

}
