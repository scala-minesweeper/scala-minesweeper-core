package de.htwg.mps.minesweeper.view.gui

import javax.swing.UIManager

import de.htwg.mps.minesweeper.controller._
import de.htwg.mps.minesweeper.model.grid.Grid

import scala.swing.Color
import scala.swing.event.Key
import scala.swing.{Action, BorderPanel, Frame, GridPanel, Menu, MenuBar, MenuItem, Swing, TextField}


class SwingGui(controller: GameController) extends Frame {

  listenTo(controller)

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  title = "Minesweeper"

  var status = new TextField("Initializing", 20)

  redraw()
  visible = true

  private def redraw(): Unit = contents = new BorderPanel {
    add(new GamePanel(controller.game.grid(), controller), BorderPanel.Position.Center)
    add(status, BorderPanel.Position.South)
  }


  reactions += {
    case _: GameStart =>
      status.text = "Game is running"
      redraw()
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

}
