package de.htwg.mps.minesweeper.view.gui

import de.htwg.mps.minesweeper.controller.GameController

import scala.swing.{Action, BorderPanel, Button, Frame, Label, TextField}

class NewGameGui(controller: GameController) extends Frame {

  title = "Start new game"

  contents = new BorderPanel() {

    private def labelsPanel: BorderPanel = new BorderPanel() {
      val labelRows: Label = new Label {
        text = "Reihen"
      }
      val labelCols: Label = new Label {
        text = "Spalten"
      }
      val labelBombs: Label = new Label {
        text = "Minen"
      }

      add(labelRows, BorderPanel.Position.North)
      add(labelCols, BorderPanel.Position.Center)
      add(labelBombs, BorderPanel.Position.South)
    }

    private def restartButton: Button = new Button {
      text = "restart"
      action = Action("restart") {
        controller.restartGame(textFieldRows.text.toInt, textFieldCols.text.toInt, textFieldBombs.text.toInt)
        dispose()
      }
    }

    val textFieldRows: TextField = new TextField {
      text = controller.game.grid().getSize._1.toString
    }
    val textFieldCols: TextField = new TextField {
      text = controller.game.grid().getSize._2.toString
    }
    val textFieldBombs: TextField = new TextField {
      text = controller.game.grid().bombs.toString
    }

    private def textFieldPanel: BorderPanel = new BorderPanel() {
      add(textFieldCols, BorderPanel.Position.North)
      add(textFieldRows, BorderPanel.Position.Center)
      add(textFieldBombs, BorderPanel.Position.South)
    }

    add(labelsPanel, BorderPanel.Position.West)
    add(textFieldPanel, BorderPanel.Position.Center)
    add(restartButton, BorderPanel.Position.East)

  }


}
