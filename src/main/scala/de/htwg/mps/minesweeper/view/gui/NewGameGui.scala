package de.htwg.mps.minesweeper.view.gui

import javax.swing.border.EmptyBorder

import scala.swing.{Action, BorderPanel, BoxPanel, Button, Frame, Label, Orientation}

class NewGameGui(controller: GuiController) extends Frame {

  title = "Start new game"

  contents = new BorderPanel() {
    add(heading, BorderPanel.Position.North)
    add(textFields, BorderPanel.Position.Center)
    add(buttons, BorderPanel.Position.South)

    border = new EmptyBorder(10, 10, 10, 10)

    private def restartButton: Button = new Button {
      text = "restart"
      action = Action("restart") {
        controller.restartGame(textFieldRows.value, textFieldCols.value, textFieldBombs.value)
        dispose()
      }
    }

    private lazy val textFieldRows = new DigitTextField {
      text = "5"
      columns = 10
    }
    private lazy val textFieldCols = new DigitTextField {
      text = "5"
      columns = 10
    }
    private lazy val textFieldBombs = new DigitTextField {
      text = "9"
      columns = 10
    }

    private def heading = new BorderPanel() {
      add(new Label {
        text = "Start a new minesweeper game"
        font = Constants.headingFont
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
