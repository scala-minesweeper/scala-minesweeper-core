package de.htwg.mps.minesweeper.core.model.grid

import de.htwg.mps.minesweeper.api.{Field, Grid}
import de.htwg.mps.minesweeper.core.model.field.{BombField, NumberField}
import de.htwg.mps.minesweeper.core.utils.{NumberToStringUtils, NumberUtils}

import scala.util.Random

case class MinesweeperGrid(playground: TwoDimensional[Field], bombs: Int, random: Random) extends Grid {

  override def missingBombs: Int = bombs - playground.asList.count(f => f.isFlagged || (f.isShown && f.isBomb))

  override def set(row: Int, col: Int, cell: Field): MinesweeperGrid = copy(playground = playground.updated(row, col, cell))

  override def set(coordinates: (Int, Int), cell: Field): MinesweeperGrid = set(coordinates._1, coordinates._2, cell)

  override def updateField(row: Int, col: Int, f: Field => Field): Grid =
    get(row, col).fold(this)(field => set(row, col, f(field)))

  override def get(row: Int, col: Int): Option[Field] = playground.get(row, col)

  override def get(coordinates: (Int, Int)): Option[Field] = get(coordinates._1, coordinates._2)

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

  private def sumBombNumberAround(position: (Int, Int)): Int =
    GridUtils.coordinatesAround(position).count(isBomb)

  private def isBomb(position: (Int, Int)): Boolean =
    playground.get(position._1, position._2).fold(false)(f => f.isBomb)


  override def getFieldCount: Int = playground.rows * playground.cols

  override def fields: List[Field] = playground.asList

  override def nestedFields: List[List[Field]] = playground.asNestedList

  override def toString: String = {
    val cols = playground.cols
    val rows = playground.rows
    val rowDigits = NumberUtils.numberOfDigits(rows - 1)
    val colDigits = NumberUtils.numberOfDigits(cols - 1)
    val string = 0.until(colDigits).foldRight(" ")((line, string) =>
      string + "\n" + (" " * (rowDigits + 1)) + "| " + 0.until(cols)
        .map(col => NumberToStringUtils.getCharStringAtOrElse(col, line, " "))
        .mkString(" ")
    ) + "\n" + "-" * (rowDigits + 1) + "|" + "-" * (cols * 2) + "\n"
    playground.asNestedList.zipWithIndex.foldLeft(string)((string, row) => {
      val index = row._2
      val rowList = row._1
      string + (" " * (rowDigits - NumberToStringUtils.stringLength(index))) + index + " | " + rowList.mkString(" ") + "\n"
    }) + "\nBombs to be found: " + missingBombs
  }

}

object MinesweeperGrid {
  def apply(rows: Int, cols: Int, bombs: Int): MinesweeperGrid =
    MinesweeperGrid(TwoDimensionalArray[Field](rows, cols, NumberField(0)), bombs, Random)

  def apply(rows: Int, cols: Int, bombs: Int, random: Random): MinesweeperGrid =
    MinesweeperGrid(TwoDimensionalArray[Field](rows, cols, NumberField(0)), bombs, random)
}