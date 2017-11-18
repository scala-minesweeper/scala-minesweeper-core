
import de.htwg.mps.minesweeper.controller.GameController
import de.htwg.mps.minesweeper.model.impl.Grid

object MinesweeperMain {

  val controller = new GameController(new Grid(5, 10, 10).init())

  def main(args: Array[String]) {
    println(controller.grid)
    for {
      r <- 0 to 5
      c <- 0 to 10
    } controller.openField(r, c)
    println(controller.grid)
  }
}
