package de.htwg.mps.minesweeper.view.tui

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.minesweeper.controller.{GameStart, RegisterObserver, StartGame}

class TuiActor(controller: ActorRef, publisher: ActorRef) extends Actor {

  publisher ! RegisterObserver

  controller ! StartGame(2, 2, 3)

  override def receive: PartialFunction[Any, Unit] = {
    case GameStart(grid) => println(grid)
    case _ => println
  }

}
