package de.htwg.mps.minesweeper.view.gui

import java.awt.event.MouseEvent

import de.htwg.mps.minesweeper.api.events.GridModel

import scala.swing.event.MouseClicked
import scala.swing.{FlowPanel, _}

class CellPanel(row: Int, col: Int, grid: GridModel, guiController: GuiController) extends FlowPanel {

  contents += new BoxPanel(Orientation.Vertical) {
    contents += new Label() {
      text = grid.fields(row)(col).value
      font = Constants.cellFont
      listenTo(mouse.clicks)
      listenTo(mouse.moves)
      listenTo(guiController)
      reactions += {
        case e: FieldUpdateEvent =>
          if (e.col == col && e.row == row) {
            text = e.field.value
            repaint
          }
        case e: GridUpdateEvent =>
          text = e.grid.fields(row)(col).value
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
