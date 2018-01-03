package de.htwg.mps.minesweeper.view.tui

import de.htwg.mps.minesweeper.api.events.PlayerModel

case class PlayerTuiPrinter(player: PlayerModel) extends TuiPrinter {

  def print(): String = "Player scores: \n" +
    player.history.foldLeft("")((string, result) => string + "- " + result.score + "\n")

}
