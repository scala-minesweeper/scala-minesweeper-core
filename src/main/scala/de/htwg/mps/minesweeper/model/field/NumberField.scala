package de.htwg.mps.minesweeper.model.field

case class NumberField(fieldState: FieldState, numberBombs: Int) extends Field {

  override def showField(): NumberField = copy(fieldState = FieldOpenState())

  override def flagField(): NumberField = copy(fieldState = FieldFlaggedState())

  override def questionField(): NumberField = copy(fieldState = FieldQuestionMarkedState())

  override def toggleNextFieldState(): NumberField = copy(fieldState = fieldState.nextState)

  override def isBomb: Boolean = false

  override def toString: String = {
    if (isShown) "" + numberBombs + " " else
    if (isQuestionMarked) questionMarkedFieldString else
    if (isFlagged) flaggedFieldString else
      hiddenFieldString
  }

}

object NumberField {
  def apply(numberBombs: Int): NumberField = new NumberField(FieldHiddenState(), numberBombs)
}