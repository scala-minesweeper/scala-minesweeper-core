package de.htwg.mps.minesweeper.controller

import de.htwg.mps.minesweeper.model.IGrid

class GameController(var grid: IGrid) extends IGameController {

  override def openField(row: Int, col: Int): Unit =
    grid.get(row, col).exists(cell => {
      println("Open field (" + row + "|" + col + ")")
      grid = grid.set(row, col, cell.showField())
      true
    })

  override def flagField(row: Int, col: Int): Unit =
    grid.get(row, col).exists(cell => {
      grid = grid.set(row, col, cell.flagField())
      true
    })

  override def unflagField(row: Int, col: Int): Unit =
    grid.get(row, col).exists(cell => {
      grid = grid.set(row, col, cell.unflagField())
      true
    })

  override def getGrid: IGrid = grid

}
