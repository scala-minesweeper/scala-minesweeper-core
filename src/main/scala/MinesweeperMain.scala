
import de.htwg.mps.minesweeper.controller.{GameController, IGameController}
import de.htwg.mps.minesweeper.view.gui.SwingGui
import de.htwg.mps.minesweeper.view.tui.Tui

import scala.io.StdIn.readLine

object MinesweeperMain {

  val controller: IGameController = new GameController()
  val tui: Tui = new Tui(controller)
  val gui = new SwingGui(controller)

  def main(args: Array[String]) {
    controller.restartGame()
    while (tui.processInput(readLine())) {}
  }
}
