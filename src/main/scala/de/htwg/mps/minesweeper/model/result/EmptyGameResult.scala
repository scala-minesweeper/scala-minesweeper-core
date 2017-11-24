package de.htwg.mps.minesweeper.model.result

case class EmptyGameResult() extends GameResult {
  override def getScore: Int = 0
}
