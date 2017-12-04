package de.htwg.mps.minesweeper.model.field

import scala.math.{abs, ceil, log}

trait NumberUtils {

  def numberOfDigits(x: Int): Int = ceil(log(abs(x) + 1) / log(10)).toInt

}

object NumberUtils extends NumberUtils
