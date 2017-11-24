package de.htwg.mps.minesweeper.model.field

case class BombField(fieldState: FieldState) extends Field {

  override def showField(): BombField = copy(fieldState = FieldOpenState())

  override def flagField(): BombField = copy(fieldState = FieldFlaggedState())

  override def questionField(): BombField = copy(fieldState = FieldQuestionMarkedState())

  override def toggleNextFieldState(): BombField = copy(fieldState = fieldState.nextState)

  override def isBomb: Boolean = true

  override def toString: String = {
    if (isShown) "+ " else
    if (isQuestionMarked) questionMarkedFieldString else
    if (isFlagged) flaggedFieldString else
      hiddenFieldString
  }

}

object BombField {
  def apply(): BombField = new BombField(FieldHiddenState())
}
