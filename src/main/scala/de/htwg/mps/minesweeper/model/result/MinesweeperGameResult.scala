package de.htwg.mps.minesweeper.model.result

case class MinesweeperGameResult(win: Boolean,
                                 foundBombs: Int,
                                 allBombs: Int,
                                 timeSeconds: Long,
                                 gridFields: Int) extends GameResult {
  override def getScore: Int = foundBombsScore + gridDifficultyScore + timeScore

  def foundBombsScore: Int = (if (win) 100 else 10) * foundBombs
  def gridDifficultyScore: Int = if (win) 10 * allBombs / gridFields else 0
  def timeScore: Int = (if (win) 500 * (math.max(1, gridFields * 3 - timeSeconds) / 100) else 0).toInt

  override def toString: String = "Score(" +
    "\n found bombs     : " + foundBombsScore +
    "\n grid difficulty : " + gridDifficultyScore +
    "\n time bonus      : " + timeScore +
    "\n                  -----" +
    "\n game score sum  : " + getScore +
    "\n)"
}