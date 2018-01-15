package de.htwg.mps.minesweeper.view.tui

import de.htwg.mps.minesweeper.api.events.GridModel
import de.htwg.mps.minesweeper.core.model.field.NumberUtils
import de.htwg.mps.minesweeper.core.utils.NumberToStringUtils

case class GridTuiPrinter(grid: GridModel) extends TuiPrinter {

  def print(): String = {
    val cols = grid.size._2
    val rows = grid.size._1
    val rowDigits = NumberUtils.numberOfDigits(rows - 1)
    val colDigits = NumberUtils.numberOfDigits(cols - 1)
    val string = 0.until(colDigits).foldRight(" ")((line, string) =>
      string + "\n" + (" " * (rowDigits + 1)) + "| " + 0.until(cols)
        .map(col => NumberToStringUtils.getCharStringAtOrElse(col, line, " "))
        .mkString(" ")
    ) + "\n" + "-" * (rowDigits + 1) + "|" + "-" * (cols * 2) + "\n"
    grid.fields.zipWithIndex.foldLeft(string)((string, row) => {
      val index = row._2
      val rowList = row._1
      string + (" " * (rowDigits - NumberToStringUtils.stringLength(index))) + index + " | " +
        rowList.map(_.value).mkString(" ") + "\n"
    }) + "\nBombs to be found: " + grid.missingBombs
  }

}
