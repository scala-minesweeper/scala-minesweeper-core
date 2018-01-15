package de.htwg.mps.minesweeper.view.gui

import java.awt.event.MouseEvent

import de.htwg.mps.minesweeper.api.events.GridModel
import de.htwg.mps.minesweeper.core.model.field.BombField

import scala.swing.event.MouseClicked
import scala.swing.{FlowPanel, _}

class CellPanel(row: Int, col: Int, grid: GridModel, guiController: GuiController) extends FlowPanel {

  contents += new BoxPanel(Orientation.Vertical) {
    contents += new Label() {
      private def fieldValue: String = grid.fields(row)(col).value
      text = fieldValue
      font = Constants.cellFont
      foreground = textColor(fieldValue)
      listenTo(mouse.clicks)
      listenTo(mouse.moves)
      listenTo(guiController)

      private def textColor(value: String) = {
        value match {
          case "+" => Constants.bombColor
          case "0" => Constants.nullColor
          case "#" => Constants.flagColor
          case "?" => Constants.questionColor
          case _ => Constants.textColor
        }
      }

      reactions += {
        case e: FieldUpdateEvent =>
          if (e.col == col && e.row == row) {
            text = e.field.value
            foreground = textColor(e.field.value)
            repaint
          }
        case e: GridUpdateEvent =>
          val newFieldValue = e.grid.fields(row)(col).value
          text = newFieldValue
          foreground = textColor(newFieldValue)
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
