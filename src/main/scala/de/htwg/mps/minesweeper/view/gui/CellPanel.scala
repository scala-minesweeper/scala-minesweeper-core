package de.htwg.mps.minesweeper.view.gui

import java.awt.event.MouseEvent

import de.htwg.mps.minesweeper.controller.{FieldChanged, GameController, GridChanged}
import de.htwg.mps.minesweeper.model.field.Field

import scala.swing.{FlowPanel, _}
import scala.swing.event.{MouseClicked, MouseEntered, MouseExited}

class CellPanel(row: Int, col: Int, controller: GameController) extends FlowPanel {

  private val cellFont = new Font("Verdana", 1, 36)

  def getCell: Option[Field] = controller.game.grid().get(row, col)

  contents += new BoxPanel(Orientation.Vertical) {
    contents += new Label() {
      text = getCell.getOrElse().toString
      font = cellFont
      listenTo(mouse.clicks)
      listenTo(mouse.moves)
      listenTo(controller)
      reactions += {
        case e: FieldChanged =>
          if (e.col == col && e.row == row) {
            text = e.field.toString
            repaint
          }
        case e: GridChanged =>
          text = e.grid.get(row, col).getOrElse().toString
          repaint
        case evt@MouseClicked(_, _, _, _, _) =>
          evt.peer.getButton match {
            case MouseEvent.BUTTON1 => controller.openField(row, col)
            case MouseEvent.BUTTON3 => controller.toggleMarkField(row, col)
            case _ =>
          }
        case MouseEntered(_, _, _) =>
        case MouseExited(_, _, _) =>
      }
    }
  }

}
