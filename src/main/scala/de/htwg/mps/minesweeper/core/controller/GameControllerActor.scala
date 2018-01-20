package de.htwg.mps.minesweeper.core.controller

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.minesweeper.api.events._
import de.htwg.mps.minesweeper.api.{Field, Game, Grid}
import de.htwg.mps.minesweeper.core.model.MinesweeperGame
import de.htwg.mps.minesweeper.core.model.field.NumberField
import de.htwg.mps.minesweeper.core.model.grid.{GridUtils, MinesweeperGrid}

class GameControllerActor(publisher: ActorRef, playerController: ActorRef, startGame: Game) extends Actor {

  publisher ! RegisterPublisher

  override def receive: Receive = run(startGame)

  private def run(game: Game): Receive = {
    case StartGame(rows, cols, bombs) => context.become(run(restartGame(rows, cols, bombs)))
    case OpenField(row, col) => context.become(run(openField(row, col, game)))
    case ToggleField(row, col) => context.become(run(toggleMarkField(game, row, col)))
    case GetCurrentStatus() => sender() ! GameUpdate(game)
  }

  private def restartGame(rows: Int, cols: Int, bombs: Int): Game = {
    val game = MinesweeperGame(MinesweeperGrid(rows, cols, bombs).init()).startGame()
    publisher ! GameStart(game)
    game
  }

  private def openField(row: Int, col: Int, game: Game): Game = ifGameRunning(game, row, col, cell => {
    cell match {
      case f if f.isFlagged || f.isQuestionMarked =>
        println("First remove flag or ? on field before you can open it!")
        game
      case f: NumberField if f.numberBombs == 0 =>
        val newGame = game.updateGrid(openFieldsAround(row, col, game.grid()))
        publisher ! GridUpdate(newGame.grid())
        checkAndHandleGameIsOver(newGame)
      case _ => updateField(game, "Open field", row, col, cell.showField())
    }
  })

  private def openAllFields(game: Game): Game = {
    val newGame = game.updateGrid(
      game.grid().coordinates.foldLeft(game.grid())((grid, coordinate) =>
        grid.updateField(coordinate._1, coordinate._2, field => field.showField())
      )
    )
    publisher ! GridUpdate(newGame.grid())
    newGame
  }

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

  private def toggleMarkField(game: Game, row: Int, col: Int): Game =
    ifGameRunning(game, row, col, cell =>
      updateField(game, row, col, cell.toggleNextFieldState())
    )

  private def ifGameRunning(game: Game, f: () => Game): Game = if (game.isRunning) {
    f()
  } else game

  private def ifGameRunning(game: Game, row: Int, col: Int, f: Field => Game): Game =
    ifGameRunning(game, () => game.grid().get(row, col).fold(game)(cell => {
      f(cell)
    }))

  private def updateField(game: Game, row: Int, col: Int, field: Field): Game = {
    val newGame = game.updateGrid(game.grid().set(row, col, field))
    publisher ! FieldUpdate(row, col, field, newGame.grid())
    checkAndHandleGameIsOver(newGame)
  }

  private def updateField(game: Game, actionText: String, row: Int, col: Int, field: Field): Game = {
    println(actionText + " " + coordinateToString(row, col))
    updateField(game, row, col, field)
  }

  private def checkAndHandleGameIsOver(game: Game): Game = {
    if (game.checkWin) {
      finishGameWin(game)
    } else if (game.checkLost) {
      finishGameLost(game)
    } else {
      game
    }
  }

  private def finishGameWin(game: Game): Game = {
    val newGame = game.finishGame()
    val wonMessage = GameWon(newGame)
    publisher ! wonMessage
    playerController ! wonMessage
    newGame
  }

  private def finishGameLost(game: Game): Game = {
    val newGame = openAllFields(game.finishGame())
    val lostMessage = GameLost(newGame)
    publisher ! lostMessage
    playerController ! lostMessage
    newGame
  }

  private def coordinateToString(row: Int, col: Int): String = "(" + row + "|" + col + ")"

}

object GameControllerActor {
  def apply(publisher: ActorRef, playerController: ActorRef): GameControllerActor =
    new GameControllerActor(publisher, playerController, MinesweeperGame())
}
