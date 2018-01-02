package de.htwg.mps.minesweeper.view.gui

import de.htwg.mps.minesweeper.api.Grid

import scala.swing.{Color, GridPanel}

class GamePanel(grid: Grid, guiController: GuiController)
  extends GridPanel(grid.getSize._1, grid.getSize._2) {

  private val backgroundColor = new Color(255, 255, 255)

  background = backgroundColor

  redraw()

  private def redraw(): Unit = {
    contents.clear()
    for {
      row <- 0 until grid.getSize._1
      column <- 0 until grid.getSize._2
    } contents += new CellPanel(row, column, grid, guiController)
  }

}
