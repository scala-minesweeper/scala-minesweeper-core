package de.htwg.mps.minesweeper.model.player

import de.htwg.mps.minesweeper.model.result.GameResult

trait Player {

  val history: List[GameResult]

}
