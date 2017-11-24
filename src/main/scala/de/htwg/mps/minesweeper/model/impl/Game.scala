package de.htwg.mps.minesweeper.model.impl

import com.github.nscala_time.time.Imports._
import de.htwg.mps.minesweeper.model.result.{EmptyGameResult, GameResult, MinesweeperGameResult}
import de.htwg.mps.minesweeper.model.{IGame, IGrid, IPlayer}

case class Game(start: DateTime,
                end: DateTime,
                running: Boolean,
                grid: IGrid,
                player: IPlayer,
                gameResult: GameResult) extends IGame {
  override def startGame(): IGame = copy(start = DateTime.now, running = true)

  override def finishGame(): IGame = {
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

  override def updateGrid(grid: IGrid): IGame = copy(grid = grid)

  override def isRunning: Boolean = running

  override def isFinished: Boolean = running && !start.equals(end)

  override def getScore: Option[GameResult] = gameResult match {
    case _: EmptyGameResult => None
    case e => Some(e)
  }
}

object Game {
  def apply(): Game = Game(
    DateTime.now,
    DateTime.now,
    running = false,
    Grid(1, 1, 0),
    Player(),
    EmptyGameResult()
  )
  def apply(grid: IGrid): Game = Game(
    DateTime.now,
    DateTime.now,
    running = false,
    grid,
    Player(),
    EmptyGameResult()
  )
}