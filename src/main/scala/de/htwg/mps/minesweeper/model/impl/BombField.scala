package de.htwg.mps.minesweeper.model.impl

class BombField(isShown: Boolean, isFlagged: Boolean, numberBombs: Int)
  extends NumberField(isShown, isFlagged, numberBombs) {

  override def isBomb: Boolean = true

  override def toString: String = "+"
}

object BombField {
  def apply(): BombField = new BombField(false, false, 0)
}
