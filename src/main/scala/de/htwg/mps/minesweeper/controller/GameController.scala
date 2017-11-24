package de.htwg.mps.minesweeper.controller

import de.htwg.mps.minesweeper.model.impl.{Game, Grid}
import de.htwg.mps.minesweeper.model.result.{EmptyGameResult, MinesweeperGameResult}
import de.htwg.mps.minesweeper.model.{IField, IGame, IGrid}

import scala.swing.event.Event

case class FieldChanged(row: Int, col: Int, field: IField) extends Event

case class GameWon() extends Event

case class GameLost() extends Event

case class GameStart(grid: IGrid) extends Event

class GameController() extends IGameController {

  var grid: IGrid = Grid(1, 1, 0)
  var game: IGame = Game(grid)

  override def restartGame(): Unit = {
    grid = Grid(3, 3, 3).init()
    game = Game(grid).startGame()
    publish(GameStart(grid))
  }

  override def openAllFields(): Unit = running(() =>
    grid.getCoordinates.foreach(coordinate => openField(coordinate._1, coordinate._2))
  )

  override def openField(row: Int, col: Int): Unit = running(() =>
    grid.get(row, col).exists(cell => updateField("Open field", row, col, cell.showField()))
  )

  override def questionField(row: Int, col: Int): Unit = running(() =>
    grid.get(row, col).exists(cell => updateField("Mark field (?)", row, col, cell.questionField()))
  )

  override def unQuestionField(row: Int, col: Int): Unit = running(() =>
    grid.get(row, col).exists(cell => updateField("Unmark field (?)", row, col, cell.unQuestionField()))
  )

  override def flagField(row: Int, col: Int): Unit = running(() =>
    grid.get(row, col).exists(cell => updateField(row, col, cell.flagField()))
  )

  override def unflagField(row: Int, col: Int): Unit = running(() =>
    grid.get(row, col).exists(cell => updateField(row, col, cell.unflagField()))
  )

  override def getGrid: IGrid = grid

  /**
    * Only execute the given function, if the current game is running.
    *
    * @param f function to execute
    * @tparam U function type
    */
  private def running[U](f: () => U): Unit = if (game.isRunning) f()

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
    checkIfGameIsOver()
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

  private def checkIfGameIsOver(): Unit = {
    if (grid.checkIfGameIsWon) finishGameWin()
    if (grid.checkIfGameIsLost) finishGameLost()
  }

  private def finishGameWin(): Unit = {
    game = game.finishGame()
    println(game.getScore.getOrElse(EmptyGameResult()))
    publish(GameWon())
  }

  private def finishGameLost(): Unit = {
    game = game.finishGame()
    println(game.getScore.getOrElse(EmptyGameResult()))
    publish(GameLost())
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
