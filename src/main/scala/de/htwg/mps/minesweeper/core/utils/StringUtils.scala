package de.htwg.mps.minesweeper.core.utils

trait StringUtils {

  def getCharStringAtOrElse(input: Int, position: Int, orElse: String): String = {
    if (stringLength(input) <= position) {
      orElse
    } else {
      input.toString.substring(position, position + 1)
    }
  }

  def stringLength(x: Int): Int = x.toString.length

}

object StringUtils extends StringUtils

