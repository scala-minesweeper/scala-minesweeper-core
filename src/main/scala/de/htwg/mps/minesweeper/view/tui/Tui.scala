package de.htwg.mps.minesweeper.view.tui

import de.htwg.mps.minesweeper.controller.IGameController

class Tui(val controller: IGameController) {

  def printTui(): Unit = {
    println("Minesweeper")
    println(controller.getGrid.toString)
    println("You can choose following actions")
    println(" <row> <col> - open a cell")
    println(" p - print the field")
    println(" q - quit the game")
  }

  def processInput(input: String): Boolean = {
    input match {
      case "p" =>
        println(controller.getGrid.toString)
        true
      case "q" =>
        println("Goodbye")
        false
      case _ =>
        input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: column :: Nil =>
            controller.openField(row, column)
            println(controller.getGrid.toString)
          case _ => println("Unknown action")
        }
        true
    }
  }

}
