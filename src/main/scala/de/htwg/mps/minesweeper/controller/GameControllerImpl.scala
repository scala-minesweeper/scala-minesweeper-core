package de.htwg.mps.minesweeper.controller

import de.htwg.mps.minesweeper.model.field.Field
import de.htwg.mps.minesweeper.model.grid.{Grid, MinesweeperGrid}
import de.htwg.mps.minesweeper.model.player.{MinesweeperPlayer, Player}
import de.htwg.mps.minesweeper.model.result.{EmptyGameResult, GameResult}
import de.htwg.mps.minesweeper.model.{Game, MinesweeperGame}

import scala.swing.event.Event

case class FieldChanged(row: Int, col: Int, field: Field) extends Event

case class GridChanged(grid: Grid) extends Event

case class GameWon(gameResult: GameResult) extends Event

case class GameLost(gameResult: GameResult) extends Event

case class GameStart(grid: Grid) extends Event

case class PlayerUpdate(player: Player) extends Event

class GameControllerImpl() extends GameController {

  var game: Game = MinesweeperGame()
  var player: Player = MinesweeperPlayer()

  override def restartGame(rows: Int, cols: Int, bombs: Int): Unit = {
    game = MinesweeperGame(MinesweeperGrid(rows, cols, bombs).init()).startGame()
    publish(GameStart(game.grid()))
  }

  override def openAllFields(): Unit = {
    game = game.updateGrid(
      game.grid().coordinates.foldLeft(game.grid())((grid, coordinate) =>
        grid.updateField(coordinate._1, coordinate._2, field => field.showField())
      )
    )
    publish(GridChanged(game.grid()))
  }

  override def openField(row: Int, col: Int): Unit = running(row, col, cell => {
    // do not open field if it is flagged or marked
    if (!cell.isFlagged && !cell.isQuestionMarked)
      updateField("Open field", row, col, cell.showField())
    else {
      println("First remove flag or ? on field before you can open it!")
      true
    }
  })

  override def questionField(row: Int, col: Int): Unit = running(row, col, cell =>
    updateField("Mark field (?)", row, col, cell.questionField())
  )

  override def flagField(row: Int, col: Int): Unit = running(row, col, cell =>
    updateField(row, col, cell.flagField())
  )

  override def toggleMarkField(row: Int, col: Int): Unit = running(row, col, cell =>
    updateField(row, col, cell.toggleNextFieldState())
  )

  /**
    * Only execute the given function, if the current game is running.
    *
    * @param f function to execute
    * @tparam U function type (unused)
    */
  private def running[U](f: () => U): Unit = if (game.isRunning) f()

  /**
    * Execute the given function for a field on the given coordinates.
    * The function is only executed, if the game is
    * running and there is a field on the given coordinate.
    *
    * @param row row coordinate for a field
    * @param col column coordinate for a field
    * @param f   function to execute for this field
    * @tparam U function type (unused)
    */
  private def running[U](row: Int, col: Int, f: Field => U): Unit =
    running(() => game.grid().get(row, col).exists(cell => {
      f(cell)
      true
    }))

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
    if(checkIfGameIsOver()) {
      game.getScore.exists(score => {
        player = player.addGameResult(score)
        publish(PlayerUpdate(player))
        true
      })
    }
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

  private def checkIfGameIsOver(): Boolean = {
    if (game.checkWin) {
      finishGameWin()
      true
    } else if (game.checkLost) {
      finishGameLost()
      true
    } else {
      false
    }
  }

  private def finishGameWin(): Unit = {
    game = game.finishGame()
    publish(GameWon(game.getScore.getOrElse(EmptyGameResult())))
  }

  private def finishGameLost(): Unit = {
    game = game.finishGame()
    publish(GameLost(game.getScore.getOrElse(EmptyGameResult())))
    openAllFields()
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
