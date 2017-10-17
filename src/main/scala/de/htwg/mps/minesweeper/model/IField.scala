package de.htwg.mps.minesweeper.model

trait IField {
  def showField(): Unit
  def flagField(): Unit
  def unflagField(): Unit
  def isBomb: Boolean
}
