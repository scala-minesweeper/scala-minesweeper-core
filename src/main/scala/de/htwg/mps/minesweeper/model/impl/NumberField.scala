package de.htwg.mps.minesweeper.model.impl

case class NumberField(number: Int) extends Field {

  override def isBomb: Boolean = false
}
