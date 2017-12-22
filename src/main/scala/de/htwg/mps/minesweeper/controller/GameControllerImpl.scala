package de.htwg.mps.minesweeper.controller

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.minesweeper.model.field.{Field, NumberField}
import de.htwg.mps.minesweeper.model.grid.{Grid, GridUtils, MinesweeperGrid}
import de.htwg.mps.minesweeper.model.player.{MinesweeperPlayer, Player}
import de.htwg.mps.minesweeper.model.result.EmptyGameResult
import de.htwg.mps.minesweeper.model.{Game, MinesweeperGame}

class GameControllerImpl(publisher: ActorRef) extends GameController with Actor {

  var game: Game = MinesweeperGame()
  var player: Player = MinesweeperPlayer()

  override def receive: PartialFunction[Any, Unit] = {
    case StartGame(rows, cols, bombs) => restartGame(rows, cols, bombs)
    case OpenField(row, col) => openField(row, col)
    case ToggleField(row, col) => toggleMarkField(row, col)
  }

  override def restartGame(rows: Int, cols: Int, bombs: Int): Unit = {
    game = MinesweeperGame(MinesweeperGrid(rows, cols, bombs).init()).startGame()
    publisher ! GameStart(game.grid())
  }

  override def openAllFields(): Unit = {
    game = game.updateGrid(
      game.grid().coordinates.foldLeft(game.grid())((grid, coordinate) =>
        grid.updateField(coordinate._1, coordinate._2, field => field.showField())
      )
    )
    publisher ! GridChanged(game.grid())
  }

  override def openField(row: Int, col: Int): Unit = running(row, col, cell => {
    cell match {
      case f if f.isFlagged || f.isQuestionMarked =>
        println("First remove flag or ? on field before you can open it!")
        true
      case f: NumberField if f.numberBombs == 0 =>
        game = game.updateGrid(openFieldsAround(row, col, game.grid()))
        publisher ! GridChanged(game.grid())
      case _ => updateField("Open field", row, col, cell.showField())
    }
  })

  private def openFieldsAround(row: Int, col: Int, codeGrid: Grid): Grid = {

    def openFieldsAroundHelper(internalGrid: Grid, coordinate: (Int, Int)): Grid = {
      internalGrid.get(coordinate).fold(internalGrid)({
        case f if f.isShown || f.isQuestionMarked || f.isFlagged => internalGrid
        case f: NumberField if f.numberBombs == 0 =>
          GridUtils.coordinatesAround(coordinate)
            .foldLeft(
              internalGrid.set(coordinate, f.showField())
            )(
              openFieldsAroundHelper
            )
        case f => internalGrid.set(coordinate, f.showField())
      })
    }

    openFieldsAroundHelper(codeGrid, (row, col))
  }

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
    publisher ! FieldChanged(row, col, field)
    if (checkIfGameIsOver()) {
      game.getScore.exists(score => {
        player = player.addGameResult(score)
        publisher ! PlayerUpdate(player)
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
    publisher ! GameWon(game.getScore.getOrElse(EmptyGameResult()))
  }

  private def finishGameLost(): Unit = {
    game = game.finishGame()
    publisher ! GameLost(game.getScore.getOrElse(EmptyGameResult()))
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
