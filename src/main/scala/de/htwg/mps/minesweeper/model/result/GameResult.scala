package de.htwg.mps.minesweeper.model.result

trait GameResult {
  def getScore: Int

  def win: Boolean
}