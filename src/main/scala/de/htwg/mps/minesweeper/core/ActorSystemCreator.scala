package de.htwg.mps.minesweeper.core

import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.config.Config
import de.htwg.mps.minesweeper.core.controller.{GameControllerActor, PlayerControllerActor, PublisherActor}

class ActorSystemCreator(config: Config) {

  private val actorSystemName = config.getString("akka.minesweeper.systemName")
  private val controllerActorName = config.getString("akka.minesweeper.controllerActor")
  private val publisherActorName = config.getString("akka.minesweeper.publisherActor")

  val system = ActorSystem(actorSystemName)

  val publisher: ActorRef =
    system.actorOf(Props[PublisherActor], publisherActorName)

  val playerController: ActorRef =
    system.actorOf(Props(PlayerControllerActor(publisher)))
  val gameController: ActorRef =
    system.actorOf(Props(GameControllerActor(publisher, playerController)), controllerActorName)

}

object ActorSystemCreator {
  def apply(config: Config): ActorSystemCreator = new ActorSystemCreator(config)
}