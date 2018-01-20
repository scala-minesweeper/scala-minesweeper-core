package de.htwg.mps.minesweeper.core.model.game


import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{TestKit, TestProbe}
import de.htwg.mps.minesweeper.api._
import de.htwg.mps.minesweeper.api.events._
import de.htwg.mps.minesweeper.core.controller.{GameControllerActor, PlayerControllerActor, PublisherActor}
import org.scalatest._

import scala.concurrent.duration._
import scala.language.postfixOps

class MinesweeperGameFlagBombFieldWinTest extends TestKit(ActorSystem("TestSystem"))
  with Matchers with FlatSpecLike with BeforeAndAfterAll {

  val timeout: FiniteDuration = 2000.millis

  override def afterAll: Unit = {
    shutdown(system)
  }

  "A 1x1 Grid" should "game win" in {

    val testProbePlayerController = TestProbe()

    val publisher: ActorRef =
      system.actorOf(Props[PublisherActor])
    val playerController: ActorRef =
      system.actorOf(Props(PlayerControllerActor(publisher)))
    val gameController: ActorRef =
      system.actorOf(Props(GameControllerActor(testProbePlayerController.ref, playerController)))

    gameController ! StartGame(1, 1, 1)
    gameController ! ToggleField(0, 0)

    testProbePlayerController.expectMsg(timeout, RegisterPublisher)
    testProbePlayerController.expectMsg(timeout, GameStart(GameModel(finished = false, running = true, None, GridModel(1, 1, (1, 1), List(List(FieldModel(FieldHiddenState, "~")))))))
    testProbePlayerController.expectMsg(timeout, FieldUpdate(0, 0, FieldModel(FieldFlaggedState, "#"), GridModel(1, 0, (1, 1), List(List(FieldModel(FieldFlaggedState, "#"))))))

    testProbePlayerController.expectMsg(timeout, GameWon(new GameModel(false, false, Some(GameResult(win = true, 1, 1, 0, 1)), GridModel(1, 0, (1, 1), List(List(FieldModel(FieldFlaggedState, "#")))))))

  }

}
