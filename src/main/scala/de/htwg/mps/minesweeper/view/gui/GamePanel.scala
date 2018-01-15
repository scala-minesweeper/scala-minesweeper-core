package de.htwg.mps.minesweeper.view.gui

import de.htwg.mps.minesweeper.api.events.GridModel

import scala.swing.{Color, GridPanel}

class GamePanel(grid: GridModel, guiController: GuiController) extends GridPanel(grid.size._1, grid.size._2) {

  private val backgroundColor = new Color(255, 255, 255)

  background = backgroundColor

  redraw()

  private def redraw(): Unit = {
    contents.clear()
    for {
      row <- 0 until grid.size._1
      column <- 0 until grid.size._2
    } contents += new CellPanel(row, column, grid, guiController)
  }

}
