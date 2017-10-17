
import de.htwg.mps.minesweeper.model.impl.{Field, Grid}

import Array._

object MinesweeperMain {

  val playground: Array[Array[Field]] = ofDim[Field](10,10)
  val grid = new Grid(playground)

  def main(args: Array[String])
  {
    println("Hello World")
    var (a,b) = Pair(40,42)
  }
}
