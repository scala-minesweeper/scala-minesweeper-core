package de.htwg.mps.minesweeper.view.tui

import de.htwg.mps.minesweeper.controller.IGameController

class Tui(val controller: IGameController) {

  def printTui(): Unit = {
    println("Minesweeper")
    println(controller.getGrid.toString)
    println("You can choose following actions")
    println(" o <row> <col> - open a cell")
    println(" ? <row> <col> - question mark cell")
    println(" ! <row> <col> - unquestion mark cell")
    println(" f <row> <col> - flag cell")
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
        input.toList.filter(c => c != ' ').map(c => c.toString) match {
          case "o" :: row :: column :: Nil =>
            controller.openField(row.toInt, column.toInt)
            println(controller.getGrid.toString)
          case "?" :: row :: column :: Nil =>
            controller.questionField(row.toInt, column.toInt)
            println(controller.getGrid.toString)
          case "!" :: row :: column :: Nil =>
            controller.unquestionField(row.toInt, column.toInt)
            println(controller.getGrid.toString)
          case "f" :: row :: column :: Nil =>
            controller.flagField(row.toInt, column.toInt)
            println(controller.getGrid.toString)
          case _ => println("Unknown action")
        }
        true
    }
  }

}
