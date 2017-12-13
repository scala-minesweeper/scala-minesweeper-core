package de.htwg.mps.minesweeper.controller


import de.htwg.mps.minesweeper.model.Game
import de.htwg.mps.minesweeper.model.player.Player

import scala.swing.Publisher

trait GameController extends scala.AnyRef with Publisher {

  def restartGame(rows: Int, cols: Int, bombs: Int): Unit
  def openField(row: Int, col: Int): Unit
  def openAllFields(): Unit
  def questionField(row: Int, col: Int): Unit
  def flagField(row: Int, col: Int): Unit
  def toggleMarkField(row: Int, col: Int): Unit
  def game: Game
  def player: Player

}
