
import de.htwg.mps.minesweeper.model.impl.Grid

object MinesweeperMain {

  val grid = new Grid(3,10,8)

  def main(args: Array[String])
  {
    grid.init()
    println(grid)
  }
}
