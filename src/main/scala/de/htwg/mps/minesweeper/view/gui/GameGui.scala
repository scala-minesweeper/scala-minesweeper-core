package de.htwg.mps.minesweeper.view.gui

import javax.swing.UIManager

import de.htwg.mps.minesweeper.controller._
import de.htwg.mps.minesweeper.model.grid.Grid

import scala.swing.event.Key
import scala.swing.{Action, BorderPanel, Frame, Label, Menu, MenuBar, MenuItem}


class GameGui(guiController: GuiController) extends Frame {

  listenTo(guiController)

  reactions += {
    case GameStart(grid) =>
      status.text = "Game is running"
      redraw(grid)
    case GameWon(gameResult) =>
      status.text = "You win - Score: " + gameResult.getScore
    case GameLost(gameResult) =>
      status.text = "You lost - Score: " + gameResult.getScore
  }

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  title = "Minesweeper"

  var status = new Label("Initializing")

  visible = true

  private def redraw(grid: Grid): Unit = contents = new BorderPanel {
    add(new GamePanel(grid, guiController), BorderPanel.Position.Center)
    add(status, BorderPanel.Position.South)
  }


  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {
        new NewGameGui(guiController).visible = true
      })
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
  }

}
