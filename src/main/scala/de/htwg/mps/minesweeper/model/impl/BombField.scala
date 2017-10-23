package de.htwg.mps.minesweeper.model.impl

class BombField() extends NumberField {
  override def isBomb: Boolean = true

  override def toString: String = "+"
}

object BombField {
  def apply(): BombField = new BombField
}
