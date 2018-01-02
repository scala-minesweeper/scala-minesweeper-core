package de.htwg.mps.minesweeper.controller

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.minesweeper.model.player.{MinesweeperPlayer, Player}
import de.htwg.mps.minesweeper.model.result.GameResult

class PlayerControllerActor(publisher: ActorRef) extends Actor {

  publisher ! RegisterPublisher

  override def receive: Receive = run(MinesweeperPlayer())

  private def run(player: Player): Receive = {
    case GameWon(game) =>
      game.getScore.fold()(score => context.become(addGameResult(player, score)))
    case GameLost(game) =>
      game.getScore.fold()(score => context.become(addGameResult(player, score)))
    case GetCurrentStatus() => sender() ! PlayerUpdate(player)
  }

  private def addGameResult(player: Player, gameResult: GameResult) = {
    val newPlayer = player.addGameResult(gameResult)
    publisher ! PlayerUpdate(newPlayer)
    run(newPlayer)
  }

}
