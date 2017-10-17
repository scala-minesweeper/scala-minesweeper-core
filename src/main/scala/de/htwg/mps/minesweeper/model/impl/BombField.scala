package de.htwg.mps.minesweeper.model.impl

case class BombField() extends NumberField {
  override def isBomb: Boolean = true

  override def toString: String = "+"
}
