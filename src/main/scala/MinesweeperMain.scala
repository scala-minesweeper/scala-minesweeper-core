
import de.htwg.mps.minesweeper.model.impl.Grid

object MinesweeperMain {

  val grid = new Grid(5, 10, 10)

  def main(args: Array[String]) {
    val resultGrid = grid.init()
    println(resultGrid)
  }
}
