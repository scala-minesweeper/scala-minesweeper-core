package de.htwg.mps.minesweeper.controller

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.minesweeper.model.player.{MinesweeperPlayer, Player}
import de.htwg.mps.minesweeper.model.result.GameResult

class PlayerControllerActor(publisher: ActorRef) extends Actor {

  override def receive: Receive = run(MinesweeperPlayer())

  private def run(player: Player): Receive = {
    case GameWon(gameResult) => context.become(addGameResult(player, gameResult))
    case GameLost(gameResult) => context.become(addGameResult(player, gameResult))
  }

  private def addGameResult(player: Player, gameResult: GameResult) = {
    val newPlayer = player.addGameResult(gameResult)
    publisher ! PlayerUpdate(newPlayer)
    run(newPlayer)
  }

}
