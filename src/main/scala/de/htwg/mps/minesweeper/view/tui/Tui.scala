package de.htwg.mps.minesweeper.view.tui

import de.htwg.mps.minesweeper.controller._

import scala.swing.Reactor

class Tui(val controller: IGameController) extends Reactor {

  listenTo(controller)
  reactions += {
    case _: FieldChanged => printTui()
    case _: GameWon => printTui("You win")
    case _: GameLost => printTui("You lost")
    case _: GameStart =>
      println("\n==========================\nMinesweeper\n==========================")
      printTui()
  }

  def printTui(): Unit = {
    println(controller.getGrid.toString)
    println("You can choose following actions")
    println(" o <row> <col> - open a cell")
    println(" ? <row> <col> - question mark cell")
    println(" ! <row> <col> - unquestion mark cell")
    println(" f <row> <col> - flag cell")
    println(" p - print the field")
    println(" a - open all fields (dev cheat)")
    println(" r - restart the game with new fields")
    println(" q - quit the game")
  }

  def printTui(message: String): Unit = {
    println(message)
  }

  def processInput(input: String): Boolean = {
    input match {
      case "p" => continue(() => println(controller.getGrid.toString))
      case "q" => stop(() => println("Goodbye"))
      case "a" => continue(() => controller.openAllFields())
      case "r" => continue(() => controller.restartGame())
      case _ =>
        continue(() =>
          input.split("\\s+").toList match {
            case "o" :: row :: column :: Nil =>
              controller.openField(row.toInt, column.toInt)
            case "?" :: row :: column :: Nil =>
              controller.questionField(row.toInt, column.toInt)
            case "!" :: row :: column :: Nil =>
              controller.unQuestionField(row.toInt, column.toInt)
            case "f" :: row :: column :: Nil =>
              controller.flagField(row.toInt, column.toInt)
            case _ => println("Unknown action")
          }
        )
    }
  }

  private def continue[U](f: () => U): Boolean = {
    f()
    true
  }

  private def stop[U](f: () => U): Boolean = {
    f()
    false
  }

}
