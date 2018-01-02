package de.htwg.mps.minesweeper.core.model.result

import de.htwg.mps.minesweeper.api.GameResult

case class EmptyGameResult() extends GameResult {
  override def getScore: Int = 0

  override def win: Boolean = false
}
