package de.htwg.mps.minesweeper.model.grid

import de.htwg.mps.minesweeper.model.field.{BombField, Field, NumberField}

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
    var string = "   | " + 0.until(math.min(11, playground.cols)).mkString("  ")
    if (playground.cols > 10) string += " " + 11.until(playground.cols).mkString(" ")
    string += "\n"
    string += "-" * 3 + "|" + "-" * (playground.cols * 3) + "\n"
    var rowIndex = 0
    playground.asNestedList.foreach(row => {
      if (rowIndex < 10) string += rowIndex + "  | " + row.mkString(" ") + "\n" else string += rowIndex + " | " + row.mkString(" ") + "\n"
      rowIndex += 1
    })
    string
  }
}

object MinesweeperGrid {
  def apply(rows: Int, cols: Int, bombs: Int): MinesweeperGrid =
    MinesweeperGrid(TwoDimensionalArray[Field](rows, cols, NumberField(0)), bombs, Random)
}