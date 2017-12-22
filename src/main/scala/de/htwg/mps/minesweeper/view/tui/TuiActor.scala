package de.htwg.mps.minesweeper.view.tui

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.minesweeper.controller._
import de.htwg.mps.minesweeper.model.grid.Grid

case class ProcessTuiInput(input: String)

class TuiActor(controller: ActorRef, publisher: ActorRef) extends Actor {

  publisher ! RegisterObserver

  override def receive: Receive = {
    case ProcessTuiInput(input) => processInput(input)
    case FieldChanged(_, _, _, grid) => println(grid)
    case GridChanged(grid) => println(grid)
    case GameWon(gameResult) => println(gameResult)
    case GameLost(gameResult) => println(gameResult)
    case GameStart(grid) =>
      println("\n==========================\nMinesweeper\n==========================")
      printTui(grid)
    case PlayerUpdate(player) => printTui(PlayerTuiPrinter(player).print())
  }

  private def printTui(grid: Grid): Unit = {
    println(grid)
    println("You can choose following actions")
    println(" o <row> <col> - open a cell")
    println(" ! <row> <col> - toggle cell mark (#: flagged, ?: question marked)")
    println(" r - restart the game with new fields")
  }

  private def printTui(message: String): Unit = {
    println(message)
  }

  private def processInput(input: String): Unit = {
    input match {
      case "r" => controller ! StartGame(4, 5, 3)
      case _ =>
        input.split("\\s+").toList match {
          case "o" :: row :: column :: Nil =>
            controller ! OpenField(row.toInt, column.toInt)
          case "!" :: row :: column :: Nil =>
            controller ! ToggleField(row.toInt, column.toInt)
          case _ => println("Unknown action")
        }
    }

  }

}
