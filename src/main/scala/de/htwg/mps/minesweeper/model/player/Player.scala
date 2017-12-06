package de.htwg.mps.minesweeper.model.player

import de.htwg.mps.minesweeper.model.result.GameResult

trait Player {

  def history: List[GameResult]
  def addGameResult(gameResult: GameResult): Player

}
