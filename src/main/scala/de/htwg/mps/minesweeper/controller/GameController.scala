package de.htwg.mps.minesweeper.controller


import de.htwg.mps.minesweeper.model.Game

import scala.swing.Publisher

trait GameController extends Publisher {

  def restartGame(): Unit
  def openField(row: Int, col: Int): Unit
  def openAllFields(): Unit
  def questionField(row: Int, col: Int): Unit
  def flagField(row: Int, col: Int): Unit
  def toggleMarkField(row: Int, col: Int): Unit
  def game: Game

}
