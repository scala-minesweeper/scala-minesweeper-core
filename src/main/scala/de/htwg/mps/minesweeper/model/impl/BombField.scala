package de.htwg.mps.minesweeper.model.impl

case class BombField() extends Field {
  override def isBomb: Boolean = true
}
