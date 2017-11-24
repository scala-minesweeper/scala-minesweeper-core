package de.htwg.mps.minesweeper.model.field

trait Field {

  protected val hiddenFieldString = "~ "

  protected val questionMarkedFieldString = "? "

  protected val flaggedFieldString = "# "

  def showField(): Field

  def flagField(): Field

  def questionField(): Field

  def unQuestionField(): Field

  def unflagField(): Field

  def isBomb: Boolean

  def isFlagged: Boolean

  def isShown: Boolean

}
