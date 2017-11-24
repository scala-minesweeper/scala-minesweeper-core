package de.htwg.mps.minesweeper.model.field

sealed trait FieldState {
  def nextState: FieldState
}

case class FieldOpenState() extends FieldState {
  override def nextState: FieldState = this
}
case class FieldFlaggedState() extends FieldState {
  override def nextState: FieldState = FieldQuestionMarkedState()
}
case class FieldQuestionMarkedState() extends FieldState {
  override def nextState: FieldState = FieldHiddenState()
}
case class FieldHiddenState() extends FieldState {
  override def nextState: FieldState = FieldFlaggedState()
}
