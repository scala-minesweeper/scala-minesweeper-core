package de.htwg.mps.minesweeper.model.field

case class BombField(fieldState: FieldState) extends Field {

  override protected val openedFieldString: String = "+ "

  override def showField(): BombField = copy(fieldState = FieldOpenState())

  override def flagField(): BombField = copy(fieldState = FieldFlaggedState())

  override def questionField(): BombField = copy(fieldState = FieldQuestionMarkedState())

  override def toggleNextFieldState(): BombField = copy(fieldState = fieldState.nextState)

  override def isBomb: Boolean = true
}

object BombField {
  def apply(): BombField = new BombField(FieldHiddenState())
}
