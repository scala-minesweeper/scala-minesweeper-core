package de.htwg.mps.minesweeper.controller

import de.htwg.mps.minesweeper.model.game.IGame

import scala.swing.Publisher

trait IGameController extends Publisher {

  def restartGame(): Unit
  def openField(row: Int, col: Int): Unit
  def openAllFields(): Unit
  def questionField(row: Int, col: Int): Unit
  def unQuestionField(row: Int, col: Int): Unit
  def flagField(row: Int, col: Int): Unit
  def unflagField(row: Int, col: Int): Unit
  def game: IGame

}
