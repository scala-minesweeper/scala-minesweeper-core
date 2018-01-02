package de.htwg.mps.minesweeper.view.gui

import java.awt.event.MouseEvent

import de.htwg.mps.minesweeper.api.Grid

import scala.swing.event.MouseClicked
import scala.swing.{FlowPanel, _}

class CellPanel(row: Int, col: Int, grid: Grid, guiController: GuiController) extends FlowPanel {

  contents += new BoxPanel(Orientation.Vertical) {
    contents += new Label() {
      text = grid.get(row, col).getOrElse().toString
      font = Constants.cellFont
      listenTo(mouse.clicks)
      listenTo(mouse.moves)
      listenTo(guiController)
      reactions += {
        case e: FieldUpdateEvent =>
          if (e.col == col && e.row == row) {
            text = e.field.toString
            repaint
          }
        case e: GridUpdateEvent =>
          text = e.grid.get(row, col).getOrElse().toString
          repaint
        case evt@MouseClicked(_, _, _, _, _) =>
          evt.peer.getButton match {
            case MouseEvent.BUTTON1 => guiController.openField(row, col)
            case MouseEvent.BUTTON3 => guiController.toggleMarkField(row, col)
          }
      }
    }
  }

}
