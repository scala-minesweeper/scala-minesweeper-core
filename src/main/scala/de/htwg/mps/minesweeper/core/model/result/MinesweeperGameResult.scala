package de.htwg.mps.minesweeper.core.model.result

import de.htwg.mps.minesweeper.api.GameResult

case class MinesweeperGameResult(win: Boolean,
                                 foundBombs: Int,
                                 allBombs: Int,
                                 timeSeconds: Long,
                                 gridFields: Int) extends GameResult {
  override def getScore: Int = foundBombsScore + gridDifficultyScore + timeScore

  def foundBombsScore: Int = (if (win) 100 else 10) * foundBombs

  def gridDifficultyScore: Int = (if (win) (500.0 * allBombs) / gridFields else 0).toInt

  def timeScore: Int = (if (win) 500.0 * (math.max(1.0, gridFields * 3 - timeSeconds) / 100) else 0).toInt

  override def toString: String =
    "Score(" +
      "\n You " + (if (win) "win" else "lost") + "!!" +
      "\n found bombs     : " + foundBombsScore +
      "\n grid difficulty : " + gridDifficultyScore +
      "\n time bonus      : " + timeScore +
      "\n                  -----" +
      "\n game score sum  : " + getScore +
      "\n)"
}