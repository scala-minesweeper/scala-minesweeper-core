package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IField

case class NumberField(isShown: Boolean, isFlagged: Boolean, numberBombs: Int) extends IField {

  override def showField(): IField = copy(isShown = true)

  override def flagField(): IField = copy(isFlagged = true)

  override def unflagField(): IField = copy(isFlagged = false)

  override def isBomb: Boolean = false

  override def toString: String = if(isShown) "" + numberBombs else hiddenFieldString

}

object NumberField {
  def apply(numberBombs: Int): NumberField = new NumberField(false, false, numberBombs)
}