package de.htwg.mps.minesweeper.core.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{TestKit, TestProbe}
import de.htwg.mps.minesweeper.api._
import de.htwg.mps.minesweeper.api.events._
import de.htwg.mps.minesweeper.core.model.MinesweeperGame
import org.scalatest._

import scala.concurrent.duration._
import scala.language.postfixOps

class GameControllerTest extends TestKit(ActorSystem("TestSystem"))
  with Matchers with WordSpecLike with BeforeAndAfterEach {

  val timeout: FiniteDuration = 2000.millis

  override def afterEach: Unit = {
    shutdown(system)
  }

  "A game controller " can {
    "answer the GetCurrentStatus message in initial state and " should {

      val testPublisherController = TestProbe()
      val testProbePlayerController = TestProbe()
      val testProbeClient = TestProbe()

      val gameController: ActorRef =
        system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref)))

      gameController.tell(GetCurrentStatus(), testProbeClient.ref)

      "return an initial game object " in {
        testProbeClient.expectMsg(timeout, GameUpdate(MinesweeperGame()))
      }
    }

    "answer the GetCurrentStatus message after game restart and " should {

      val testPublisherController = TestProbe()
      val testProbePlayerController = TestProbe()
      val testProbeClient = TestProbe()

      val gameController: ActorRef =
        system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref)))

      gameController ! StartGame(1, 1, 1)
      gameController.tell(GetCurrentStatus(), testProbeClient.ref)

      "return the configured game object " in {
        testPublisherController.expectMsg(timeout, RegisterPublisher)
        testProbeClient.expectMsg(timeout, GameUpdate(
          GameModel(finished = false, running = true, None,
            GridModel(1, 1, (1, 1), List(List(FieldModel(FieldHiddenState, "~"))))))
        )
      }
    }

    "disallow open a field in a 1x1 game which is not running and " should {

      val testPublisherController = TestProbe()
      val testProbePlayerController = TestProbe()

      val gameController: ActorRef =
        system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref)))

      gameController ! StartGame(1, 1, 0)
      gameController ! OpenField(0, 0)

      "not send any message " in {
        // catch initializing messages first
        testPublisherController.expectMsg(timeout, RegisterPublisher)
        testPublisherController.expectMsg(timeout, GameStart(GameModel(finished = false, running = true, None,
          GridModel(0, 0, (1, 1), List(List(FieldModel(FieldHiddenState, "~")))))))
        testPublisherController.expectMsg(timeout,
          GridUpdate(GridModel(0, 0, (1, 1), List(List(FieldModel(FieldOpenState, "0"))))))
        testPublisherController.expectMsgType[GameWon](timeout)

        // then test message on nun running game
        gameController ! OpenField(0, 0)
        testPublisherController.expectNoMsg(timeout)
      }
    }

  }

}
