package de.htwg.mps.minesweeper.controller

import de.htwg.mps.minesweeper.model.Game
import de.htwg.mps.minesweeper.model.field.Field
import de.htwg.mps.minesweeper.model.grid.Grid
import de.htwg.mps.minesweeper.model.player.Player

import scala.swing.event.Event

trait GameEvent extends Event

case class FieldUpdate(row: Int, col: Int, field: Field, grid: Grid) extends GameEvent

case class GridUpdate(grid: Grid) extends GameEvent

case class GameUpdate(game: Game) extends GameEvent

case class GameWon(game: Game) extends GameEvent

case class GameLost(game: Game) extends GameEvent

case class GameStart(game: Game) extends GameEvent

case class PlayerUpdate(player: Player) extends GameEvent