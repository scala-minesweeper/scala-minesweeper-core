package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IGrid

import scala.util.Random

case class Grid(playground: TwoDimensionalArray[NumberField], bombs: Int, random: Random) extends IGrid {
  def this(rows: Int, cols: Int, bombs: Int) = this(new TwoDimensionalArray[NumberField](rows, cols, NumberField(0)), bombs, Random)

  def set(row: Int, col: Int, cell: NumberField): Grid = copy(playground = playground.updated(row, col, cell))

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
    var sum = 0
    sum += sumBombs(row - 1, col - 1)
    sum += sumBombs(row - 1, col)
    sum += sumBombs(row - 1, col + 1)
    sum += sumBombs(row, col - 1)
    sum += sumBombs(row, col + 1)
    sum += sumBombs(row + 1, col - 1)
    sum += sumBombs(row + 1, col)
    sum += sumBombs(row + 1, col + 1)
    sum
  }

  private def sumBombs(row: Int, col: Int): Int = {
    playground.get(row, col).map(f => if (f.isBomb) 1 else 0).getOrElse(0)
  }

  override def toString: String = {
    var string = "Grid(\n"
    playground.foreachRow(row => {
      row.foreach(field => string += " " + field.toString)
      string += "\n"
    })
    string += ")"
    string
  }
}
