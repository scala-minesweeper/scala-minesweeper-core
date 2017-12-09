package de.htwg.mps.minesweeper.view.gui

import java.awt.event.MouseEvent
import java.awt.{Color, Font}
import javax.swing.UIManager

import de.htwg.mps.minesweeper.controller._
import de.htwg.mps.minesweeper.model.grid.Grid

import scala.swing.event.{Key, MouseClicked}
import scala.swing.{Action, BorderPanel, FlowPanel, Frame, GridPanel, Label, Menu, MenuBar, MenuItem, Swing, TextField}



class SwingGui(controller: GameController) extends Frame {

  listenTo(controller)

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  title = "Minesweeper"

  var status = new TextField("Initializing", 20)

  contents = new BorderPanel {

    add(gridPanel, BorderPanel.Position.Center)
    add(status, BorderPanel.Position.South)
  }

  visible = true

  reactions += {
    case _: FieldChanged => redraw()
    case _: GameStart =>
      status.text = "Game is running"
      redraw()
    case _: GridChanged => redraw()
    case g: GameWon =>
      status.text = "You win - Score: " + g.gameResult.getScore
    case g: GameLost =>
      status.text = "You lost - Score: " + g.gameResult.getScore
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {
        new NewGameGui(controller).visible = true
      })
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
  }

  private def redraw(): Unit = resize()

  private def resize(): Unit = contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
    add(status, BorderPanel.Position.South)
  }

  private def gridPanel: GridPanel = new GridPanel(
    controller.game.grid().getSize._1,
    controller.game.grid().getSize._2) {
    border = Swing.LineBorder(Color.BLACK, 2)
    background = Color.WHITE
    val grid: Grid = controller.game.grid()
    for {
      row <- 0 until grid.getSize._1
      column <- 0 until grid.getSize._2
    } {
      contents += new FlowPanel() {
        contents += new Label() {
          text = grid.get(row, column).getOrElse().toString
          font = new Font("Verdana", 1, 36)
          listenTo(mouse.clicks)
          reactions += {
            case evt@MouseClicked(_, _, _, _, _) =>
              evt.peer.getButton match {
                case MouseEvent.BUTTON1 => controller.openField(row, column)
                case MouseEvent.BUTTON3 => controller.toggleMarkField(row, column)
                case _ =>
              }
          }
        }
      }
    }
  }
}
