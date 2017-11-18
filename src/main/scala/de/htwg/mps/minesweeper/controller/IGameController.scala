package de.htwg.mps.minesweeper.controller

import de.htwg.mps.minesweeper.model.IGrid

trait IGameController {

  def openField(row: Int, col: Int): Unit
  def flagField(row: Int, col: Int): Unit
  def unflagField(row: Int, col: Int): Unit
  def getGrid: IGrid

}
