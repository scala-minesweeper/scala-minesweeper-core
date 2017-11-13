
import de.htwg.mps.minesweeper.model.impl.{Grid, NumberField, TwoDimensionalArray}

import scala.util.Random

object MinesweeperMain {

  val grid = Grid(new TwoDimensionalArray[NumberField](5, 10, new NumberField(false, false, 0)), Random)

  def main(args: Array[String]) {
    val resultGrid = grid.init()
    println(resultGrid)
  }
}
