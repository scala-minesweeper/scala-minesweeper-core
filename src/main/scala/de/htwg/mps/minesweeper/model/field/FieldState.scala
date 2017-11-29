package de.htwg.mps.minesweeper.model.field

sealed trait FieldState {
  def nextState: FieldState
}

case object FieldOpenState extends FieldState {
  override def nextState: FieldState = this
}
case object FieldFlaggedState extends FieldState {
  override def nextState: FieldState = FieldQuestionMarkedState
}
case object FieldQuestionMarkedState extends FieldState {
  override def nextState: FieldState = FieldHiddenState
}
case object FieldHiddenState extends FieldState {
  override def nextState: FieldState = FieldFlaggedState
}