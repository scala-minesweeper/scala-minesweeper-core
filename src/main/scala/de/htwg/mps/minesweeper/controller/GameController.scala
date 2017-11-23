package de.htwg.mps.minesweeper.controller

import de.htwg.mps.minesweeper.model.{IField, IGrid}

import scala.swing.event.Event

case class FieldChanged(row: Int, col: Int, field: IField) extends Event
case class GameOver(won: Boolean) extends Event

class GameController(var grid: IGrid) extends IGameController {

  override def openAllFields(): Unit =
    grid.getCoordinates.foreach(coordinate => openField(coordinate._1, coordinate._2))

  override def openField(row: Int, col: Int): Unit =
    grid.get(row, col).exists(cell => updateField("Open field", row, col, cell.showField()))

  override def questionField(row: Int, col: Int): Unit =
    grid.get(row, col).exists(cell => updateField("Mark field (?)", row, col, cell.questionField()))

  override def unQuestionField(row: Int, col: Int): Unit =
    grid.get(row, col).exists(cell => updateField("Unmark field (?)", row, col, cell.unQuestionField()))

  override def flagField(row: Int, col: Int): Unit =
    grid.get(row, col).exists(cell => updateField(row, col, cell.flagField()))

  override def unflagField(row: Int, col: Int): Unit =
    grid.get(row, col).exists(cell => updateField(row, col, cell.unflagField()))

  override def getGrid: IGrid = grid

  /**
    * Update a field and notify all reactors about the changed field in grid.
    *
    * @param row   row number of field
    * @param col   column number of field
    * @param field new field
    * @return true, if successfully updated
    */
  private def updateField(row: Int, col: Int, field: IField): Boolean = {
    grid = grid.set(row, col, field)
    publish(FieldChanged(row, col, field))
    checkIfGameIsOver
    true
  }

  /**
    * Update a field and notify all reactors about the changed field in grid.
    * Additionally print an action, which was done.
    *
    * @param actionText an action text to print
    * @param row        row number of field
    * @param col        column number of field
    * @param field      new field
    * @return true, if successfully updated
    */
  private def updateField(actionText: String, row: Int, col: Int, field: IField): Boolean = {
    println(actionText + " " + coordinateToString(row, col))
    updateField(row, col, field)
  }


  /**
    * Check in the grid if all fields were found which are not bombs.
    * If yes, all reactors are notified and the game will be finished successfully.
    */
  private def checkIfGameIsOver(): Unit = {
    if(grid.checkIfGameIsWon ) publish(GameOver(true))
  }

  /**
    * Convert a coordinate to string for printing.
    *
    * @param row row number of a field
    * @param col column number of a field
    * @return printable coordinate string
    */
  private def coordinateToString(row: Int, col: Int): String = "(" + row + "|" + col + ")"

}
