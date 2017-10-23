package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IGrid

import scala.Array._
import scala.collection.mutable.ListBuffer
import scala.util.Random

case class Grid(width: Int, height: Int, bombs: Int) extends IGrid {
  // TODO bomb calculation
  def this(value: Int) = this(value, value, (value - 1) * 2)

  var playground: Array[Array[NumberField]] = ofDim[NumberField](width, height)

  def init(): Unit = {
    val list = mapCoordinatesIntoList
    val bombList = ListBuffer[(Int, Int)]()


    1.to(Math.min(bombs, list.length)).foreach(_ => {
      val randomValue = Random.nextInt(list.length)
      val position = list.remove(randomValue)
      bombList.+=:(position._1, position._2)
      playground(position._1)(position._2) = BombField()

    })

    list.foreach((position) => playground(position._1)(position._2) = NumberField())

    bombList.foreach((position) => incrementBombNumberAround(position._1, position._2))

  }

  private def mapCoordinatesIntoList: ListBuffer[(Int, Int)] = {
    val list = ListBuffer[(Int, Int)]()

    1.to(width).foreach(a => {
      1.to(height).foreach(b => {
        list.+=:(a - 1, b - 1)
      })
    })

    list
  }


  private def incrementBombNumberAround(x: Int, y: Int): Unit = {
    incrementBombNumber(x - 1, y - 1)
    incrementBombNumber(x - 1, y)
    incrementBombNumber(x - 1, y + 1)
    incrementBombNumber(x, y - 1)
    incrementBombNumber(x, y + 1)
    incrementBombNumber(x + 1, y - 1)
    incrementBombNumber(x + 1, y)
    incrementBombNumber(x + 1, y + 1)
  }

  private def incrementBombNumber(x: Int, y: Int): Unit = {
    getPosition(x, y).exists(f => {
      f.incrementNumberBombsBeside()
      true
    })
  }

  def getPosition(x: Int, y: Int): Option[NumberField] = {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      None
    } else {
      Option(playground(x)(y))
    }
  }

  override def toString: String = {
    var string = "Grid(\n"
    1.to(width).foreach(a => {
      1.to(height).foreach(b => {
        string += " " + playground(a - 1)(b - 1).toString
      })
      string += "\n"
    })
    string += ")"
    string
  }
}
