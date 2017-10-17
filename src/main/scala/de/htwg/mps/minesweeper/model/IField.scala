package de.htwg.mps.minesweeper.model
import de.htwg.mps.minesweeper.model.impl.NumberField

trait IField {
  def showField: NumberField
  def flagField: NumberField
  def unflagField: NumberField
}
