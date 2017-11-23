package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IField

case class NumberField(isShown: Boolean, isFlagged: Boolean, isQuestionMarked: Boolean, numberBombs: Int) extends IField {

  override def showField(): NumberField = copy(isShown = true)

  override def flagField(): NumberField = copy(isFlagged = true, isQuestionMarked = false)

  override def unflagField(): NumberField = copy(isFlagged = false)

  override def questionField(): NumberField = copy(isQuestionMarked = true, isFlagged = false)

  override def unQuestionField(): NumberField = copy(isQuestionMarked = false)

  override def isBomb: Boolean = false

  override def toString: String = if(isShown) "" + numberBombs+" " else if(isQuestionMarked) questionMarkedFieldString else if(isFlagged) flaggedFieldString else hiddenFieldString


}

object NumberField {
  def apply(numberBombs: Int): NumberField = new NumberField(false, false, false, numberBombs)
}