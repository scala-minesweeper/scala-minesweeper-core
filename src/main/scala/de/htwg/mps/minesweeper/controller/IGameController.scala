package de.htwg.mps.minesweeper.controller

trait IGameController {

  def openField(row: Int, col: Int)
  def flagField(row: Int, col: Int)
  def unflagField(row: Int, col: Int)

}
