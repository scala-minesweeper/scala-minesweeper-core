
import de.htwg.mps.minesweeper.model.impl.Grid

import Array._

object MinesweeperMain {

  //val playground: Array[Array[Field]] = ofDim[Field](10,10)
  val grid = new Grid(3,10,8)

  def main(args: Array[String])
  {
    grid.init()
    println(grid)
  }
}
