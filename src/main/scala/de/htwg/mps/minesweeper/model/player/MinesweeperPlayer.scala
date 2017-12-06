package de.htwg.mps.minesweeper.model.player

import de.htwg.mps.minesweeper.model.result.GameResult

case class MinesweeperPlayer(history: List[GameResult]) extends Player {

  def addGameResult(gameResult: GameResult): Player = copy(history = history.::(gameResult))

}
