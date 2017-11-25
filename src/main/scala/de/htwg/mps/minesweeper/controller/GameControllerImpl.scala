package de.htwg.mps.minesweeper.controller

import de.htwg.mps.minesweeper.model.{Game, MinesweeperGame}
import de.htwg.mps.minesweeper.model.field.{Field, FieldHiddenState}
import de.htwg.mps.minesweeper.model.grid.{Grid, MinesweeperGrid}
import de.htwg.mps.minesweeper.model.result.{EmptyGameResult, GameResult}

import scala.swing.event.Event

case class FieldChanged(row: Int, col: Int, field: Field) extends Event

case class GameWon() extends Event

case class GameLost(gameResult: GameResult) extends Event

case class GameStart(grid: Grid) extends Event

class GameControllerImpl() extends GameController {

  var game: Game = MinesweeperGame()

  override def restartGame(rows: Int, cols: Int, bombs: Int): Unit = {
    game = MinesweeperGame(MinesweeperGrid(rows, cols, bombs).init()).startGame()
    publish(GameStart(game.grid()))
  }

  override def openAllFields(): Unit = running(() =>
    game.grid().getCoordinates.foreach(coordinate => openField(coordinate._1, coordinate._2))
  )

  override def openField(row: Int, col: Int): Unit = running(() =>
    game.grid().get(row, col).exists(cell => {
      if (!cell.isFlagged && !cell.isQuestionMarked)
        // do not open field if it is flagged or marked
        updateField("Open field", row, col, cell.showField())
      else
        println("First unmark field before you can open it!")
        true
    })
  )

  override def questionField(row: Int, col: Int): Unit = running(() =>
    game.grid().get(row, col).exists(cell => updateField("Mark field (?)", row, col, cell.questionField()))
  )

  override def flagField(row: Int, col: Int): Unit = running(() =>
    game.grid().get(row, col).exists(cell => updateField(row, col, cell.flagField()))
  )

  override def toggleMarkField(row: Int, col: Int): Unit = running(() =>
    game.grid().get(row, col).exists(cell => updateField(row, col, cell.toggleNextFieldState()))
  )

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
  private def updateField(row: Int, col: Int, field: Field): Boolean = {
    game = game.updateGrid(game.grid().set(row, col, field))
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
  private def updateField(actionText: String, row: Int, col: Int, field: Field): Boolean = {
    println(actionText + " " + coordinateToString(row, col))
    updateField(row, col, field)
  }

  private def checkIfGameIsOver(): Unit = {
    if (game.checkWin) finishGameWin()
    if (game.checkLost) finishGameLost()
  }

  private def finishGameWin(): Unit = {
    game = game.finishGame()
    println(game.getScore.getOrElse(EmptyGameResult()))
    publish(GameWon())
  }

  private def finishGameLost(): Unit = {

    println(game.getScore.getOrElse(EmptyGameResult()))
    publish(GameLost(game.getScore.getOrElse(EmptyGameResult())))
    game = game.finishGame()
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
