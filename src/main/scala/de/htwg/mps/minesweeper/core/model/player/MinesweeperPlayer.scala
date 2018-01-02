package de.htwg.mps.minesweeper.core.model.player

import de.htwg.mps.minesweeper.api.{GameResult, Player}

case class MinesweeperPlayer(history: List[GameResult]) extends Player {

  def addGameResult(gameResult: GameResult): Player = copy(history = history.::(gameResult))

}

object MinesweeperPlayer {
  def apply(): MinesweeperPlayer = MinesweeperPlayer(List[GameResult]())
}
