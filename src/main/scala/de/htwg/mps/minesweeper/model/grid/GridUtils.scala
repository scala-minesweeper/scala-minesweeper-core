package de.htwg.mps.minesweeper.model.grid

import scala.collection.breakOut

trait GridUtils {

  def coordinatesAround(coordinate: (Int, Int)): List[(Int, Int)] =
    (for {
      r <- coordinate._1 - 1 to coordinate._1 + 1
      c <- coordinate._2 - 1 to coordinate._2 + 1
      if r != coordinate._1 || c != coordinate._2
    } yield (r, c)) (breakOut)

}

object GridUtils extends GridUtils
