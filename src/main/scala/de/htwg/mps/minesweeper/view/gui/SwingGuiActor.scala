package de.htwg.mps.minesweeper.view.gui

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.minesweeper.controller._

class SwingGuiActor(controller: ActorRef, publisher: ActorRef) extends Actor {

  publisher ! RegisterObserver

  private val guiController = new GuiController(controller)
  new GameGui(guiController)

  override def receive: Receive = {
    case event: GameEvent => guiController.sendEvent(event)
  }

}
