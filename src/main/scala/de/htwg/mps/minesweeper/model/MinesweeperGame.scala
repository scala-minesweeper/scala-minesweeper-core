package de.htwg.mps.minesweeper.model

import com.github.nscala_time.time.Imports._
import de.htwg.mps.minesweeper.model.grid._
import de.htwg.mps.minesweeper.model.result.{EmptyGameResult, GameResult, MinesweeperGameResult}

case class MinesweeperGame(start: DateTime,
                           end: DateTime,
                           running: Boolean,
                           grid: Grid,
                           gameResult: GameResult) extends Game {
  override def startGame(): Game = copy(start = DateTime.now, running = true)

  override def finishGame(): Game = {
    val now = DateTime.now
    copy(end = now, running = false,
      gameResult = MinesweeperGameResult(
        grid.checkIfGameIsWon,
        grid.correctlyFlaggedBombs,
        grid.bombs,
        start.to(now).toDuration.getStandardSeconds,
        grid.getCoordinates.size
      )
    )
  }

  override def updateGrid(grid: Grid): Game = copy(grid = grid)

  override def isRunning: Boolean = running

  override def isFinished: Boolean = running && !start.equals(end)

  override def getScore: Option[GameResult] = gameResult match {
    case _: EmptyGameResult => None
    case e => Some(e)
  }
}

object MinesweeperGame {
  def apply(): MinesweeperGame = MinesweeperGame(
    DateTime.now,
    DateTime.now,
    running = false,
    MinesweeperGrid(1, 1, 0),
    EmptyGameResult()
  )
  def apply(grid: Grid): MinesweeperGame = MinesweeperGame(
    DateTime.now,
    DateTime.now,
    running = false,
    grid,
    EmptyGameResult()
  )
}