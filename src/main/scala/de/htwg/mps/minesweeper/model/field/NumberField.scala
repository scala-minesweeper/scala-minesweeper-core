package de.htwg.mps.minesweeper.model.field

case class NumberField(fieldState: FieldState, numberBombs: Int) extends Field {

  override protected val openedFieldString: String = "" + numberBombs

  override def showField(): NumberField = copy(fieldState = FieldOpenState())

  override def flagField(): NumberField = copy(fieldState = FieldFlaggedState())

  override def questionField(): NumberField = copy(fieldState = FieldQuestionMarkedState())

  override def toggleNextFieldState(): NumberField = copy(fieldState = fieldState.nextState)

  override def isBomb: Boolean = false
}

object NumberField {
  def apply(numberBombs: Int): NumberField = new NumberField(FieldHiddenState(), numberBombs)
}