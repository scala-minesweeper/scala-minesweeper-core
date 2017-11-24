package de.htwg.mps.minesweeper.model

import de.htwg.mps.minesweeper.model.grid.Grid
import de.htwg.mps.minesweeper.model.result.GameResult

trait Game {
  def startGame(): Game
  def finishGame(): Game
  def updateGrid(grid: Grid): Game
  def grid(): Grid
  def isRunning: Boolean
  def isFinished: Boolean
  def getScore: Option[GameResult]
}