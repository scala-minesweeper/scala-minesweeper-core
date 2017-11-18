
import de.htwg.mps.minesweeper.controller.{GameController, IGameController}
import de.htwg.mps.minesweeper.model.IGrid
import de.htwg.mps.minesweeper.model.impl.Grid
import de.htwg.mps.minesweeper.view.tui.Tui

import scala.io.StdIn.readLine

object MinesweeperMain {

  val grid: IGrid = Grid(5, 10, 10).init()
  val controller: IGameController = new GameController(grid)
  val tui: Tui = new Tui(controller)

  def main(args: Array[String]) {
    tui.printTui()
    while (tui.processInput(readLine())) {}
  }
}
