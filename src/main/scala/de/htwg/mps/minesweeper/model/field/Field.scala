package de.htwg.mps.minesweeper.model.field

trait Field {

  protected val hiddenFieldString = "~ "

  protected val questionMarkedFieldString = "? "

  protected val flaggedFieldString = "# "

  def fieldState: FieldState

  def showField(): Field

  def flagField(): Field

  def questionField(): Field

  def toggleNextFieldState(): Field

  def isBomb: Boolean

  def isFlagged: Boolean = fieldState == FieldFlaggedState()

  def isQuestionMarked: Boolean = fieldState == FieldQuestionMarkedState()

  def isShown: Boolean = fieldState == FieldOpenState()

}
