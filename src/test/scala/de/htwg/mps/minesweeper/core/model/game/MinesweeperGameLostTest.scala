package de.htwg.mps.minesweeper.core.model.game

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import de.htwg.mps.minesweeper.api.{FieldHiddenState, FieldOpenState, GameResult}
import de.htwg.mps.minesweeper.api.events._
import de.htwg.mps.minesweeper.core.controller.{GameControllerActor, PlayerControllerActor, PublisherActor}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

import scala.concurrent.duration._
import scala.language.postfixOps

class MinesweeperGameLostTest extends TestKit(ActorSystem("TestSystem"))
  with Matchers with FlatSpecLike with BeforeAndAfterAll {

  val timeout: FiniteDuration = 2000.millis
  
  override def afterAll: Unit = {
    shutdown(system)
  }

  "A 1x1 Grid" should "lost game" in {

    val testProbePlayerController = TestProbe()

    val publisher: ActorRef =
      system.actorOf(Props[PublisherActor])
    val playerController: ActorRef =
      system.actorOf(Props(PlayerControllerActor(publisher)))
    val gameController: ActorRef =
      system.actorOf(Props(GameControllerActor(testProbePlayerController.ref, playerController)))

    gameController ! StartGame(1, 1, 1)
    gameController ! OpenField(0, 0)
    testProbePlayerController.expectMsg(timeout, RegisterPublisher)
    testProbePlayerController.expectMsg(timeout, GameStart(GameModel(finished = false, running = true, None, GridModel(1, 1, (1, 1), List(List(FieldModel(FieldHiddenState, "~")))))))
    testProbePlayerController.expectMsg(timeout, FieldUpdate(0, 0, FieldModel(FieldOpenState, "+"), GridModel(1, 0, (1, 1), List(List(FieldModel(FieldOpenState, "+"))))))
    testProbePlayerController.expectMsg(timeout, GridUpdate(GridModel(1, 0, (1, 1), List(List(FieldModel(FieldOpenState, "+"))))))

    testProbePlayerController.expectMsg(timeout, GameLost(new GameModel(false, false, Some(GameResult(win = false, 0, 1, 0, 1)), GridModel(1, 0, (1, 1), List(List(FieldModel(FieldOpenState, "+")))))))

  }

}
