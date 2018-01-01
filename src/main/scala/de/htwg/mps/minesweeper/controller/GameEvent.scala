package de.htwg.mps.minesweeper.controller

import de.htwg.mps.minesweeper.model.Game
import de.htwg.mps.minesweeper.model.field.Field
import de.htwg.mps.minesweeper.model.grid.Grid
import de.htwg.mps.minesweeper.model.player.Player
import de.htwg.mps.minesweeper.model.result.GameResult

import scala.swing.event.Event

trait GameEvent extends Event

// TODO remove grid from this event
case class FieldChanged(row: Int, col: Int, field: Field, grid: Grid) extends GameEvent

case class GridChanged(grid: Grid) extends GameEvent

case class GameStatus(game: Game) extends GameEvent

case class GameWon(gameResult: GameResult) extends GameEvent

case class GameLost(gameResult: GameResult) extends GameEvent

case class GameStart(grid: Grid) extends GameEvent

case class PlayerUpdate(player: Player) extends GameEvent