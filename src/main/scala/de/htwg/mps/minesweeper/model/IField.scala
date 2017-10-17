package de.htwg.mps.minesweeper.model
import de.htwg.mps.minesweeper.model.impl.NumberField

trait IField {
  def showField: Unit
  def flagField: Unit
  def unflagField: Unit
  def isBomb: Boolean
}
