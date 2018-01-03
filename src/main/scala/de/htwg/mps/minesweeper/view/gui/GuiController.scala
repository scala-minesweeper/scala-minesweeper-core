package de.htwg.mps.minesweeper.view.gui

import akka.actor.ActorRef
import de.htwg.mps.minesweeper.api.events._
import de.htwg.mps.minesweeper.api.{Field, Game, Grid, Player}

import scala.swing.Publisher
import scala.swing.event.Event

case class PlayerUpdateEvent(player: PlayerModel) extends Event

case class FieldUpdateEvent(row: Int, col: Int, field: FieldModel, grid: GridModel) extends Event

case class GridUpdateEvent(grid: GridModel) extends Event

case class GameUpdateEvent(game: GameModel) extends Event

case class GameWonEvent(game: GameModel) extends Event

case class GameLostEvent(game: GameModel) extends Event

case class GameStartEvent(game: GameModel) extends Event

class GuiController(gameController: ActorRef) extends Publisher {

  def restartGame(row: Int, col: Int, bombs: Int): Unit = gameController ! StartGame(row, col, bombs)

  def toggleMarkField(row: Int, col: Int): Unit = gameController ! ToggleField(row, col)

  def openField(row: Int, col: Int): Unit = gameController ! OpenField(row, col)

  def sendEvent(event: GameEvent): Unit = event match {
    case PlayerUpdate(p) => publish(PlayerUpdateEvent(p))
    case FieldUpdate(r, c, f, g) => publish(FieldUpdateEvent(r, c, f, g))
    case GridUpdate(p) => publish(GridUpdateEvent(p))
    case GameUpdate(p) => publish(GameUpdateEvent(p))
    case GameWon(p) => publish(GameWonEvent(p))
    case GameLost(p) => publish(GameLostEvent(p))
    case GameStart(p) => publish(GameStartEvent(p))
  }

}


