package de.htwg.mps.minesweeper.view.tui

import de.htwg.mps.minesweeper.model.player.Player

case class PlayerTuiPrinter(player: Player) extends TuiPrinter {

  def print(): String = "Player scores: \n" +
    player.history.foldLeft("")((string, result) => string + "- " + result.getScore + "\n")

}
