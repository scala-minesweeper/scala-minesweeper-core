package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.{IField, IGrid}

import scala.util.Random

case class Grid(playground: TwoDimensionalArray[IField], bombs: Int, random: Random) extends IGrid {

  def set(row: Int, col: Int, cell: IField): Grid = copy(playground = playground.updated(row, col, cell))

  def get(row: Int, col: Int): Option[IField] = playground.get(row, col)

  def init(): Grid = {
    var grid = this
    val list = playground.getCoordinates

    // place bombs and remove these coordinates from list
    1.to(Math.min(bombs, list.length)).foreach(_ => {
      val randomValue = random.nextInt(list.length)
      val position = list.remove(randomValue)
      grid = grid.set(position._1, position._2, BombField())
    })
    // place number fields on other coordinates
    list.foreach((position) => {
      grid = grid.set(position._1, position._2, NumberField(grid.sumBombNumberAround(position._1, position._2)))
    })

    grid
  }

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

  override def toString: String = {
    var string = "  | " + 0.until(playground.cols).mkString(" ") + "\n"
    string += "-" * 2 + "|" + "-" * (playground.cols*2) + "\n"
    var rowIndex = 0
    playground.foreachRow(row => {
      string += rowIndex + " |"
      row.foreach(field => string += " " + field.toString)
      string += "\n"
      rowIndex += 1
    })
    string
  }
}

object Grid {
  def apply(rows: Int, cols: Int, bombs: Int): Grid =
    Grid(new TwoDimensionalArray[IField](rows, cols, NumberField(0)), bombs, Random)
}