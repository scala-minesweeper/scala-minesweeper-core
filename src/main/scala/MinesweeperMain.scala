
import de.htwg.mps.minesweeper.controller.{GameControllerImpl, GameController}
import de.htwg.mps.minesweeper.view.gui.SwingGui
import de.htwg.mps.minesweeper.view.tui.Tui

import scala.io.StdIn.readLine

object MinesweeperMain {

  val controller: GameController = new GameControllerImpl()
  val tui: Tui = new Tui(controller)
  val gui = new SwingGui(controller)

  def main(args: Array[String]) {
    controller.restartGame()
    while (tui.processInput(readLine())) {}
  }
}
