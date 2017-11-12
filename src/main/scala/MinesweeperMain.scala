
import de.htwg.mps.minesweeper.model.impl.Grid

import scala.util.Random

object MinesweeperMain {

  val grid = Grid(10, 10, 15, Random)

  def main(args: Array[String]) {
    grid.init()
    println(grid)
  }
}
