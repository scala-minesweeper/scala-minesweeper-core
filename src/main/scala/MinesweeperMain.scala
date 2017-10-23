
import de.htwg.mps.minesweeper.model.impl.Grid

object MinesweeperMain {

  val grid = Grid(10, 10, 15)

  def main(args: Array[String]) {
    grid.init()
    println(grid)
  }
}
