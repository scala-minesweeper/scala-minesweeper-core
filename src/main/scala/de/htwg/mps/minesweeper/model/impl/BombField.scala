package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IField

case class BombField(isShown: Boolean, isFlagged: Boolean, isQuestionMarked: Boolean) extends IField {

  override def showField(): BombField = copy(isShown = true)

  override def flagField(): BombField = copy(isFlagged = true)

  override def questionField(): BombField = copy(isQuestionMarked = true)

  override def unQuestionField(): BombField = copy(isQuestionMarked = false)

  override def unflagField(): BombField = copy(isFlagged = false)

  override def isBomb: Boolean = true

  override def toString: String = if(isShown) "+ " else if(isQuestionMarked) questionMarkedFieldString else if (isFlagged) flaggedFieldString else hiddenFieldString

}

object BombField {
  def apply(): BombField = new BombField(false, false, false)
}
