package de.htwg.mps.minesweeper.model

import de.htwg.mps.minesweeper.model.grid.IGrid
import de.htwg.mps.minesweeper.model.result.GameResult

trait IGame {
  def startGame(): IGame
  def finishGame(): IGame
  def updateGrid(grid: IGrid): IGame
  def grid(): IGrid
  def isRunning: Boolean
  def isFinished: Boolean
  def getScore: Option[GameResult]
}