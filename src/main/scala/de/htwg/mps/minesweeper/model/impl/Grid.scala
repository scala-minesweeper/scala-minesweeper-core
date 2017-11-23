package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.{IField, IGrid, ITwoDimensionalArray}

import scala.util.Random

case class Grid(playground: ITwoDimensionalArray[IField], bombs: Int, random: Random) extends IGrid {

  def set(row: Int, col: Int, cell: IField): Grid = copy(playground = playground.updated(row, col, cell))

  def get(row: Int, col: Int): Option[IField] = playground.get(row, col)

  def init(): Grid = {
    // shuffle all available field coordinates
    val list: List[(Int, Int)] = random.shuffle(playground.getCoordinates)
    val bombList = list.slice(0, bombs)
    val numberList = list.slice(bombs, list.size)
    // place bomb and number fields on a slice of coordinates
    numberList.foldLeft(
      bombList.foldLeft(this)(placeBombField)
    )(placeNumberField)
  }

  /**
    * compares number of opened numberFields with total number of numberfields
    *
    * @return true if number of opened numberfields equals total number of numberFields
    */
  def checkIfGameIsWon: Boolean = {
    println("detectedNonBombFields: "+ playground.detectedNonBombFields)
    println("bombs: " +bombs)
    println("playground size: "+playground.cols*playground.rows)
    playground.detectedNonBombFields == nonBombFields
  }

  override def getCoordinates: List[(Int, Int)] = playground.getCoordinates

  private def placeNumberField(grid: Grid, position: (Int, Int)): Grid =
    grid.set(position._1, position._2, NumberField(grid.sumBombNumberAround(position._1, position._2)))

  private def placeBombField(grid: Grid, position: (Int, Int)): Grid =
    grid.set(position._1, position._2, BombField())

  private def sumBombNumberAround(row: Int, col: Int): Int = {
    (for {
      r <- row - 1 to row + 1
      c <- col - 1 to col + 1
      if r != row || c != col
    } yield sumBombs(r, c)).sum
  }

  private def sumBombs(row: Int, col: Int): Int = {
    playground.get(row, col).map(f => if (f.isBomb) 1 else 0).getOrElse(0)
  }

  private def nonBombFields: Int = {
    playground.cols*playground.rows-bombs
  }

  override def toString: String = {
    var string = "   | " +0.until(math.min(11, playground.cols)).mkString("  ")
    if(playground.cols > 10) string += " "+11.until(playground.cols).mkString(" ")
    string += "\n"
    string += "-" * 3 + "|" + "-" * (playground.cols * 3) + "\n"
    var rowIndex = 0
    playground.foreachRow(row => {
      if(rowIndex < 10) string += rowIndex + "  | " + row.mkString(" ") + "\n" else string += rowIndex + " | " + row.mkString(" ") + "\n"
      rowIndex += 1
    })
    string
  }
}

object Grid {
  def apply(rows: Int, cols: Int, bombs: Int): Grid =
    Grid(TwoDimensionalArray[IField](rows, cols, NumberField(0)), bombs, Random)
}