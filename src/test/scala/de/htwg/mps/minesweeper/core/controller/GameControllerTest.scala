package de.htwg.mps.minesweeper.core.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{TestKit, TestProbe}
import com.github.nscala_time.time.Imports.DateTime
import de.htwg.mps.minesweeper.api._
import de.htwg.mps.minesweeper.api.events._
import de.htwg.mps.minesweeper.core.model.MinesweeperGame
import de.htwg.mps.minesweeper.core.model.grid.MinesweeperGrid
import org.scalatest._

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random

class GameControllerTest extends fixture.WordSpec with Matchers {

  val timeout: FiniteDuration = 2000.millis
  type FixtureParam = ActorSystem

  override def withFixture(test: OneArgTest): Outcome = {
    val testKit = new TestKit(ActorSystem("TestSystem"))
    implicit val system: ActorSystem = testKit.system

    try test(system)
    finally {
      testKit.shutdown()
    }
  }

  "A game controller " can {
    "answer the GetCurrentStatus message in initial state and" should {
      "return an initial game object " in {
        system => {
          val testPublisherController = TestProbe()(system)
          val testProbePlayerController = TestProbe()(system)
          val testProbeClient = TestProbe()

          val gameController: ActorRef =
            system.actorOf(Props(GameControllerActor(testPublisherController.ref, testProbePlayerController.ref)))

          gameController.tell(GetCurrentStatus(), testProbeClient.ref)

          testProbeClient.expectMsg(timeout, GameUpdate(MinesweeperGame()))
        }
      }
    }
  }

  "answer the GetCurrentStatus message after game restart and " should {
    "return the configured game object " in {
      system => {

        val testPublisherController = TestProbe()
        val testProbePlayerController = TestProbe()
        val testProbeClient = TestProbe()

        val gameController: ActorRef =
          system.actorOf(Props(GameControllerActor(testPublisherController.ref, testProbePlayerController.ref)), "T2")

        gameController ! StartGame(1, 1, 1)
        gameController.tell(GetCurrentStatus(), testProbeClient.ref)

        testPublisherController.expectMsg(timeout, RegisterPublisher)
        testProbeClient.expectMsg(timeout, GameUpdate(
          GameModel(finished = false, running = true, None,
            GridModel(1, 1, (1, 1), List(List(FieldModel(FieldHiddenState, "~"))))))
        )
      }
    }
  }

  "open a field in a 2x2 game with 1 bomb and " should {
    "send update message " in {
      system => {

        val testPublisherController = TestProbe()
        val testProbePlayerController = TestProbe()

        val runningGame: Game = new MinesweeperGame(DateTime.now, DateTime.now, running = true,
          MinesweeperGrid(2, 2, 1, new Random(5)).init(), None)

        val gameController: ActorRef =
          system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref, runningGame)), "T3")

        // catch initializing messages first
        testPublisherController.expectMsg(timeout, RegisterPublisher)

        gameController ! OpenField(0, 0)
        testPublisherController.expectMsg(timeout, FieldUpdate(
          0, 0, FieldModel(FieldOpenState, "1"),
          GridModel(1, 1, (2, 2), List(
            List(FieldModel(FieldOpenState, "1"), FieldModel(FieldHiddenState, "~")),
            List(FieldModel(FieldHiddenState, "~"), FieldModel(FieldHiddenState, "~"))
          )))
        )
      }
    }

  }

  "disallow open a field in a 1x1 game which is not running and " should {
    "not send any message " in {
      system => {

        val testPublisherController = TestProbe()
        val testProbePlayerController = TestProbe()

        val notRunningGame: Game = new MinesweeperGame(DateTime.now, DateTime.now, running = false,
          MinesweeperGrid(1, 1, 0, new Random(5)), None)

        val gameController: ActorRef =
          system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref, notRunningGame)), "T4")

        // catch initializing messages first
        testPublisherController.expectMsg(timeout, RegisterPublisher)

        // then test message on nun running game
        gameController ! OpenField(0, 0)
        testPublisherController.expectNoMsg(timeout)
      }
    }
  }

}
