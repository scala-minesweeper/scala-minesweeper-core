package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IGrid
import Array._
import scala.collection.mutable.ListBuffer
import scala.util.Random

class Grid(val width: Int, val height: Int, val bombs: Int) extends IGrid {
  // TODO bomb calculation
  def this(value: Int) = this(value, value, (value - 1) * 2)
  var playground: Array[Array[Field]] = ofDim[Field](width,height)

  def init(): Unit = {
    val list = ListBuffer[(Int, Int)]()
    for ( a <- 1 to width){
      for ( b <- 1 to height){
        list.+=:(a-1,b-1)
      }
    }

    for(c <- 1 to bombs){
      val randomValue = Random.nextInt(list.length)
      val position = list.remove(randomValue)
      playground(position._1)(position._2) = BombField()
    }

    for (d <- list) {
      playground(d._1)(d._2) = NumberField(0)
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
}
