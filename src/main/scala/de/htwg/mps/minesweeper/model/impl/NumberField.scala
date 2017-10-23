package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IField

class NumberField() extends IField {
  def apply(): NumberField = new NumberField()

  var isShown = false
  var isFlagged = false
  var numberBombs = 0

  def showField(): Unit = isShown = true

  def flagField(): Unit = isFlagged = true

  def unflagField(): Unit = isFlagged = false

  override def isBomb: Boolean = false

  override def toString: String = "" + numberBombs

  def incrementNumberBombsBeside(): Unit = numberBombs = numberBombs + 1

}

object NumberField {
  def apply(): NumberField = new NumberField
}