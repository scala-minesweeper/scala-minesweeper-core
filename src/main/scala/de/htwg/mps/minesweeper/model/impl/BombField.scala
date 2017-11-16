package de.htwg.mps.minesweeper.model.impl

class BombField(isShown: Boolean, isFlagged: Boolean)
  extends NumberField(isShown, isFlagged, 0) {

  override def isBomb: Boolean = true

  override def toString: String = if(isShown) "+" else hiddenFieldString
}

object BombField {
  def apply(): BombField = new BombField(false, false)
}
