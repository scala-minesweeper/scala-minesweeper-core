package de.htwg.mps.minesweeper.view.gui

import javax.swing.UIManager

import de.htwg.mps.minesweeper.api.events.GridModel

import scala.swing.event.Key
import scala.swing.{Action, BorderPanel, Frame, Label, Menu, MenuBar, MenuItem}


class GameGui(guiController: GuiController) extends Frame {

  listenTo(guiController)

  reactions += {
    case GameStartEvent(game) =>
      status.text = "Bombs to be found: " + game.grid.missingBombs
      redraw(game.grid)
    case FieldUpdateEvent(_, _, _, grid) =>
      status.text = "Bombs to be found: " + grid.missingBombs
    case GridUpdateEvent(grid) =>
      status.text = "Bombs to be found: " + grid.missingBombs
    case GameWonEvent(game) =>
      status.text = "You win - Score: " + game.gameResult.fold("?")(_.score.toString)
    case GameLostEvent(game) =>
      status.text = "You lost - Score: " + game.gameResult.fold("?")(_.score.toString)
  }

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  title = "Minesweeper"

  var status = new Label("Initializing")

  visible = true

  private def redraw(grid: GridModel): Unit = contents = new BorderPanel {
    add(new GamePanel(grid, guiController), BorderPanel.Position.Center)
    add(status, BorderPanel.Position.South)
  }


  menuBar = new MenuBar {
    contents += new Menu("New") {
      mnemonic = Key.G
      contents += new MenuItem(Action("New") {
        new NewGameGui(guiController).visible = true
      })
      contents += new MenuItem(Action("History") {
        new PlayerGui(guiController).visible = true
      })
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
  }

}
