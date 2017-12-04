package de.htwg.mps.minesweeper.view.gui

import javax.swing.border.EmptyBorder

import de.htwg.mps.minesweeper.controller.GameController

import scala.swing.{Action, BorderPanel, BoxPanel, Button, Font, Frame, Label, Orientation, TextField}

class NewGameGui(controller: GameController) extends Frame {

  title = "Start new game"

  val headingFont = new Font("Arial", 0, 20)

  contents = new BorderPanel() {
    add(heading, BorderPanel.Position.North)
    add(textFields, BorderPanel.Position.Center)
    add(buttons, BorderPanel.Position.South)

    border = new EmptyBorder(10, 10, 10, 10)

    private def restartButton: Button = new Button {
      text = "restart"
      action = Action("restart") {
        controller.restartGame(textFieldRows.text.toInt, textFieldCols.text.toInt, textFieldBombs.text.toInt)
        dispose()
      }
    }

    private lazy val textFieldRows: TextField = new TextField {
      text = controller.game.grid().getSize._1.toString
      columns = 10
    }
    private lazy val textFieldCols: TextField = new TextField {
      text = controller.game.grid().getSize._2.toString
      columns = 10
    }
    private lazy val textFieldBombs: TextField = new TextField {
      text = controller.game.grid().bombs.toString
      columns = 10
    }

    private def heading = new BorderPanel() {
      add(new Label {
        text = "Start a new minesweeper game"
        font = headingFont
      }, BorderPanel.Position.West)

      border = new EmptyBorder(0, 0, 10, 0)
    }

    private def buttons = new BorderPanel() {
      add(restartButton, BorderPanel.Position.East)
    }

    private def textFields = new BoxPanel(Orientation.Vertical) {
      contents += new BorderPanel() {
        add(inputBorderPanel("rows"), BorderPanel.Position.West)
        add(textFieldRows, BorderPanel.Position.East)

        border = new EmptyBorder(5, 0, 5, 0)
      }

      contents += new BorderPanel() {
        add(inputBorderPanel("columns"), BorderPanel.Position.West)
        add(textFieldCols, BorderPanel.Position.East)

        border = new EmptyBorder(5, 0, 5, 0)
      }

      contents += new BorderPanel() {
        add(inputBorderPanel("bombs"), BorderPanel.Position.West)
        add(textFieldBombs, BorderPanel.Position.East)

        border = new EmptyBorder(5, 0, 5, 0)
      }

      private def inputBorderPanel(textDesc: String) = new Label {
        text = textDesc
        border = new EmptyBorder(0, 0, 0, 10)
      }
    }

  }
}
