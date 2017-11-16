
import de.htwg.mps.minesweeper.controller.GameController
import de.htwg.mps.minesweeper.model.impl.Grid

object MinesweeperMain {

  val controller = new GameController(new Grid(5, 10, 10).init())

  def main(args: Array[String]) {
    println(controller.grid)
    controller.openField(2, 2)
    controller.openField(3, 5)
    controller.openField(1, 6)
    println(controller.grid)
  }
}
