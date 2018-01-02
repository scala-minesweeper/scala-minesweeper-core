package de.htwg.mps.minesweeper.core.controller

trait GameAction

case class StartGame(rows: Int, cols: Int, bombs: Int) extends GameAction

case class OpenField(row: Int, col: Int) extends GameAction

case class ToggleField(row: Int, col: Int) extends GameAction
