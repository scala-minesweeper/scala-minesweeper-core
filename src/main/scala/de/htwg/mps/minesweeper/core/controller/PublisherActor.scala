package de.htwg.mps.minesweeper.core.controller

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.minesweeper.api.events._

class PublisherActor extends Actor {

  override def receive: Receive = run(Set(), Set())

  private def run(observers: Set[ActorRef], publishers: Set[ActorRef]): Receive = {
    case RegisterObserver =>
      val newObservers = observers + sender()
      printObservers(newObservers)
      publishers.foreach(_.tell(GetCurrentStatus(), sender()))
      context.become(run(newObservers, publishers))
    case DeregisterObserver =>
      val newObservers = observers - sender()
      printObservers(newObservers)
      context.become(run(newObservers, publishers))
    case RegisterPublisher => context.become(run(observers, publishers + sender()))
    case DeregisterPublisher => context.become(run(observers, publishers - sender()))
    case event: GameEvent => observers.foreach(_ ! event)
  }

  private def printObservers(set: Set[ActorRef]): Unit = println(
    set.foldLeft("Observers:")((string, a) => string + "\n -" + a.path.toString))

}
