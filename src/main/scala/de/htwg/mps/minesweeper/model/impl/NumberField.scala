package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IField

case class NumberField(isShown: Boolean, isFlagged: Boolean, isQuestionMarked: Boolean, numberBombs: Int) extends IField {

  override def showField(): IField = copy(isShown = true)

  override def flagField(): IField = copy(isFlagged = true)

  override def unflagField(): IField = copy(isFlagged = false)

  override def questionField(): IField = copy(isQuestionMarked = true)

  override def unQuestionField(): IField = copy(isQuestionMarked = false)

  override def isBomb: Boolean = false

  override def toString: String = if(isShown) "" + numberBombs else if(isQuestionMarked) questionMarkedFieldString else hiddenFieldString


}

object NumberField {
  def apply(numberBombs: Int): NumberField = new NumberField(false, false, false, numberBombs)
}