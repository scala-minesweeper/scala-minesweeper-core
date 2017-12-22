package de.htwg.mps.minesweeper.controller

import akka.actor.{Actor, ActorRef}

case class RegisterObserver()

case class DeregisterObserver()

class GameControllerPublisher extends Actor {

  def run(observers: Set[ActorRef]): Receive = {
    case RegisterObserver => context.become(run(observers + sender()))
    case DeregisterObserver => context.become(run(observers - sender()))
    case event: GameEvent => observers.foreach(_ ! event)
  }

  override def receive: Receive = run(Set())

}