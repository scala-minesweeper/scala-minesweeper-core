package de.htwg.mps.minesweeper.controller

import de.htwg.mps.minesweeper.model.{IField, IGrid}

import scala.swing.event.Event

case class FieldChanged(row: Int, col: Int, field: IField) extends Event
case class GameWon() extends Event
case class GameLost() extends Event

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
    checkIfGameIsOver(col, row)
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
    * Check if the game is over.
    * Either because all the fields that are not bombs have been uncovered
    * or because a field with a bomb has been uncovered.
    */
  private def checkIfGameIsOver(col: Int, row: Int): Unit = {
    if(grid.checkIfGameIsWon ) publish(GameWon())
    if(grid.checkIfCoordinateIsBomb(col, row)) publish(GameLost())
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
