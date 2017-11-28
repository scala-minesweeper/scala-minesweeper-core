package de.htwg.mps.minesweeper.model.grid

import de.htwg.mps.minesweeper.model.field.{BombField, Field, NumberField}

import scala.math.{abs, ceil, log}
import scala.util.Random

case class MinesweeperGrid(playground: TwoDimensional[Field], bombs: Int, random: Random) extends Grid {

  override def set(row: Int, col: Int, cell: Field): MinesweeperGrid = copy(playground = playground.updated(row, col, cell))

  override def updateField(row: Int, col: Int, f: Field => Field): Grid =
    get(row, col).fold(this)(field => set(row, col, f(field)))

  override def get(row: Int, col: Int): Option[Field] = playground.get(row, col)

  override def init(): MinesweeperGrid = {
    // shuffle all available field coordinates
    val list: List[(Int, Int)] = random.shuffle(playground.asCoordinates)
    val bombList = list.slice(0, bombs)
    val numberList = list.slice(bombs, list.size)
    // place bomb and number fields on a slice of coordinates
    numberList.foldLeft(
      bombList.foldLeft(this)(placeBombField)
    )(placeNumberField)
  }

  override def coordinates: List[(Int, Int)] = playground.asCoordinates

  override def getSize: (Int, Int) = (playground.rows, playground.cols)

  private def placeNumberField(grid: MinesweeperGrid, position: (Int, Int)): MinesweeperGrid =
    grid.set(position._1, position._2, NumberField(grid.sumBombNumberAround(position._1, position._2)))

  private def placeBombField(grid: MinesweeperGrid, position: (Int, Int)): MinesweeperGrid =
    grid.set(position._1, position._2, BombField())

  private def sumBombNumberAround(row: Int, col: Int): Int =
    (for {
      r <- row - 1 to row + 1
      c <- col - 1 to col + 1
      if r != row || c != col
    } yield sumBombs(r, c)).sum

  private def sumBombs(row: Int, col: Int): Int =
    playground.get(row, col).map(f => if (f.isBomb) 1 else 0).getOrElse(0)

  override def getFieldCount: Int = playground.rows * playground.cols

  override def fields: List[Field] = playground.asList

  override def toString: String = {
    val cols = playground.cols
    val rows = playground.rows
    val rowDigits = numberOfDigits(rows - 1)
    val colDigits = numberOfDigits(cols - 1)
    var string = 0.until(colDigits).foldRight(" ")((line, string) => {
      string + "\n" + (" " * (rowDigits + 1)) + "| " + 0.until(cols).map(col => getChartAt(col, line)).mkString(" ")
    })
    string += "\n" + "-" * (rowDigits + 1) + "|" + "-" * (cols * 2) + "\n"
    var rowIndex = -1
    string += playground.asNestedList.foldLeft(string)((string, row) => {
      rowIndex += 1
      string + (" " * (rowDigits - stringLength(rowIndex))) + rowIndex + " | " + row.mkString(" ") + "\n"
    })
    string
  }

  private def getChartAt(input: Int, position: Int): String = {
    if (stringLength(input) <= position) {
      " "
    } else {
      input.toString.substring(position, position + 1)
    }
  }

  private def stringLength(x: Int): Int = x.toString.length

  private def numberOfDigits(x: Int): Int = ceil(log(abs(x) + 1) / log(10)).toInt

}

object MinesweeperGrid {
  def apply(rows: Int, cols: Int, bombs: Int): MinesweeperGrid =
    MinesweeperGrid(TwoDimensionalArray[Field](rows, cols, NumberField(0)), bombs, Random)
}