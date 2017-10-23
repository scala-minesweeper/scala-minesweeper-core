package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IGrid
import Array._
import scala.collection.mutable.ListBuffer
import scala.util.Random

case class Grid(width: Int, height: Int, bombs: Int) extends IGrid {
  // TODO bomb calculation
  def this(value: Int) = this(value, value, (value - 1) * 2)
  var playground: Array[Array[NumberField]] = ofDim[NumberField](width,height)

  def init(): Unit = {
    val list = ListBuffer[(Int, Int)]()
    val bombList = ListBuffer[(Int, Int)]()

    for ( a <- 1 to width){
      for ( b <- 1 to height){
        list.+=:(a-1,b-1)
      }
    }

    for(c <- 1 to bombs){
      val randomValue = Random.nextInt(list.length)
      val position = list.remove(randomValue)
      bombList.+=:(position._1, position._2)
      playground(position._1)(position._2) = BombField()
    }

    for (d <- list) {
      playground(d._1)(d._2) = NumberField()
    }

    for (e <- bombList) {
      incrementBombNumberAround(e._1, e._2)
    }
  }

  override def toString: String = {
    var string = "Grid(\n"
    for ( a <- 1 to width) {
      for (b <- 1 to height) {
        val field = playground(a-1)(b-1)
        string += " "+field.toString
      }
      string += "\n"
    }
    string += ")"
    string
  }

  def incrementBombNumberAround(x:Int, y:Int): Unit = {
    incrementBombNumber(x - 1, y - 1)
    incrementBombNumber(x - 1, y)
    incrementBombNumber(x - 1, y + 1)
    incrementBombNumber(x, y - 1)
    incrementBombNumber(x, y + 1)
    incrementBombNumber(x + 1, y - 1)
    incrementBombNumber(x + 1, y)
    incrementBombNumber(x + 1, y + 1)
  }

  def incrementBombNumber(x:Int, y:Int): Unit = {
    val field = getPosition(x, y)
    if(field != null) {
      field.incrementNumberBombsBeside()
    }
  }

  def getPosition(x:Int, y:Int): NumberField = {
    if(x < 0 || x >= width || y < 0 || y >= height) {
      null
    } else {
      playground(x)(y)
    }
  }
}
