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

class GameControllerActorTest extends fixture.WordSpec with Matchers {

  val timeout: FiniteDuration = 2000.millis
  type FixtureParam = ActorSystem

  override def withFixture(test: OneArgTest): Outcome = {
    val testKit = new TestKit(ActorSystem(Random.nextInt(Int.MaxValue).toString))
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
          val testProbeClient = TestProbe()(system)

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
        val testPublisherController = TestProbe()(system)
        val testProbePlayerController = TestProbe()(system)
        val testProbeClient = TestProbe()(system)

        val gameController: ActorRef =
          system.actorOf(Props(GameControllerActor(testPublisherController.ref, testProbePlayerController.ref)))

        testPublisherController.expectMsg(timeout, RegisterPublisher)

        gameController ! StartGame(1, 1, 1)

        testPublisherController.expectMsg(timeout, GameStart(GameModel(finished = false, running = true, None,
          GridModel(1, 1, (1, 1), List(List(FieldModel(FieldHiddenState, "~")))))))

        gameController.tell(GetCurrentStatus(), testProbeClient.ref)

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

        val testPublisherController = TestProbe()(system)
        val testProbePlayerController = TestProbe()(system)

        val runningGame: Game = new MinesweeperGame(DateTime.now, DateTime.now, running = true,
          MinesweeperGrid(2, 2, 1, new Random(5)).init(), None)

        val gameController: ActorRef =
          system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref, runningGame)))

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

  "open a field in a running game and " should {
    "in a 1x1 with 1 bomb lose the game " in {
      system => {
        val testPublisherController = TestProbe()(system)
        val testProbePlayerController = TestProbe()(system)

        val runningGame: Game = new MinesweeperGame(DateTime.now, DateTime.now, running = true,
          MinesweeperGrid(1, 1, 1, new Random(5)).init(), None)

        val gameController: ActorRef =
          system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref, runningGame)))

        // catch initializing messages first
        testPublisherController.expectMsg(timeout, RegisterPublisher)

        gameController ! OpenField(0, 0)
        testPublisherController.expectMsg(timeout, FieldUpdate(
          0, 0, FieldModel(FieldOpenState, "+"),
          GridModel(1, 0, (1, 1), List(List(FieldModel(FieldOpenState, "+"))))))
        testPublisherController.expectMsg(timeout, GridUpdate(
          GridModel(1, 0, (1, 1), List(List(FieldModel(FieldOpenState, "+"))))))

        testPublisherController.expectMsgType[GameLost](timeout)
        testProbePlayerController.expectMsgType[GameLost](timeout)
      }
    }

    "in a 1x1 with 0 bombs and win the game " in {
      system => {
        val testPublisherController = TestProbe()(system)
        val testProbePlayerController = TestProbe()(system)

        val runningGame: Game = new MinesweeperGame(DateTime.now, DateTime.now, running = true,
          MinesweeperGrid(1, 1, 0, new Random(5)).init(), None)

        val gameController: ActorRef =
          system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref, runningGame)))

        // catch initializing messages first
        testPublisherController.expectMsg(timeout, RegisterPublisher)

        gameController ! OpenField(0, 0)
        testPublisherController.expectMsg(timeout, GridUpdate(
          GridModel(0, 0, (1, 1), List(List(FieldModel(FieldOpenState, "0"))))))

        testPublisherController.expectMsgType[GameWon](timeout)
        testProbePlayerController.expectMsgType[GameWon](timeout)
      }
    }

    "in a 2x2 with 0 bombs open all fields and send a win message " in {
      system => {
        val testPublisherController = TestProbe()(system)
        val testProbePlayerController = TestProbe()(system)

        val runningGame: Game = new MinesweeperGame(DateTime.now, DateTime.now, running = true,
          MinesweeperGrid(2, 2, 0, new Random(5)).init(), None)

        val gameController: ActorRef =
          system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref, runningGame)))

        // catch initializing messages first
        testPublisherController.expectMsg(timeout, RegisterPublisher)

        gameController ! OpenField(0, 0)
        testPublisherController.expectMsg(timeout, GridUpdate(
          GridModel(0, 0, (2, 2), List(
            List(FieldModel(FieldOpenState, "0"), FieldModel(FieldOpenState, "0")),
            List(FieldModel(FieldOpenState, "0"), FieldModel(FieldOpenState, "0"))
          )))
        )
        testPublisherController.expectMsgType[GameWon](timeout)
      }
    }

    "in a 3x3 with 1 bombs open all 0 and 1 fields but not the bomb " in {
      system => {
        val testPublisherController = TestProbe()(system)
        val testProbePlayerController = TestProbe()(system)

        val runningGame: Game = new MinesweeperGame(DateTime.now, DateTime.now, running = true,
          MinesweeperGrid(3, 3, 1, new Random(7)).init(), None)

        val gameController: ActorRef =
          system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref, runningGame)))

        // catch initializing messages first
        testPublisherController.expectMsg(timeout, RegisterPublisher)

        gameController ! OpenField(0, 0)
        testPublisherController.expectMsg(timeout, GridUpdate(
          GridModel(1, 1, (3, 3), List(
            List(FieldModel(FieldOpenState, "0"), FieldModel(FieldOpenState, "0"), FieldModel(FieldOpenState, "0")),
            List(FieldModel(FieldOpenState, "0"), FieldModel(FieldOpenState, "1"), FieldModel(FieldOpenState, "1")),
            List(FieldModel(FieldOpenState, "0"), FieldModel(FieldOpenState, "1"), FieldModel(FieldHiddenState, "~"))
          )))
        )
      }
    }
  }

  "disallow open a field in a 1x1 game which is not running and " should {
    "not send any message " in {
      system => {

        val testPublisherController = TestProbe()(system)
        val testProbePlayerController = TestProbe()(system)

        val notRunningGame: Game = new MinesweeperGame(DateTime.now, DateTime.now, running = false,
          MinesweeperGrid(1, 1, 0, new Random(5)), None)

        val gameController: ActorRef =
          system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref, notRunningGame)))

        // catch initializing messages first
        testPublisherController.expectMsg(timeout, RegisterPublisher)

        // then test message on nun running game
        gameController ! OpenField(0, 0)
        testPublisherController.expectNoMsg(timeout)
      }
    }
  }

  "flag a field in a running game and " should {
    "in a 1x1 with 1 bomb win the game " in {
      system => {
        val testPublisherController = TestProbe()(system)
        val testProbePlayerController = TestProbe()(system)

        val runningGame: Game = new MinesweeperGame(DateTime.now, DateTime.now, running = true,
          MinesweeperGrid(1, 1, 1, new Random(5)).init(), None)

        val gameController: ActorRef =
          system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref, runningGame)))

        // catch initializing messages first
        testPublisherController.expectMsg(timeout, RegisterPublisher)

        gameController ! ToggleField(0, 0)

        testPublisherController.expectMsg(timeout, FieldUpdate(0, 0, FieldModel(FieldFlaggedState, "#"), GridModel(1, 0, (1, 1), List(List(FieldModel(FieldFlaggedState, "#"))))))

        testPublisherController.expectMsgType[GameWon](timeout)
        testProbePlayerController.expectMsgType[GameWon](timeout)
      }
    }

    "in a 1x1 with 0 bombs not send any message if it is requested to open " in {
      system => {
        val testPublisherController = TestProbe()(system)
        val testProbePlayerController = TestProbe()(system)

        val game: Game = new MinesweeperGame(DateTime.now, DateTime.now, running = true,
          MinesweeperGrid(1, 1, 0, new Random(5)), None)

        val gameController: ActorRef =
          system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref, game)))

        // catch initializing messages first
        testPublisherController.expectMsg(timeout, RegisterPublisher)

        // then test message on nun running game
        gameController ! ToggleField(0, 0)
        testPublisherController.expectMsg(timeout, FieldUpdate(0, 0, FieldModel(FieldFlaggedState, "#"),
          GridModel(0, -1, (1, 1), List(
            List(FieldModel(FieldFlaggedState, "#")
            )))))

        gameController ! OpenField(0, 0)
        testPublisherController.expectNoMsg(timeout)
      }
    }
  }

  "question mark a field message in a 1x1 with 0 bombs game and " should {
    "not send any message if it is requested to open " in {
      system => {
        val testPublisherController = TestProbe()(system)
        val testProbePlayerController = TestProbe()(system)

        val game: Game = new MinesweeperGame(DateTime.now, DateTime.now, running = true,
          MinesweeperGrid(1, 1, 0, new Random(5)), None)

        val gameController: ActorRef =
          system.actorOf(Props(new GameControllerActor(testPublisherController.ref, testProbePlayerController.ref, game)))

        // catch initializing messages first
        testPublisherController.expectMsg(timeout, RegisterPublisher)

        // then test message on nun running game
        gameController ! ToggleField(0, 0)
        testPublisherController.expectMsg(timeout, FieldUpdate(0, 0, FieldModel(FieldFlaggedState, "#"),
          GridModel(0, -1, (1, 1), List(
            List(FieldModel(FieldFlaggedState, "#")
            )))))

        gameController ! ToggleField(0, 0)
        testPublisherController.expectMsg(timeout, FieldUpdate(0, 0, FieldModel(FieldQuestionMarkedState, "?"),
          GridModel(0, 0, (1, 1), List(
            List(FieldModel(FieldQuestionMarkedState, "?")
            )))))

        gameController ! OpenField(0, 0)
        testPublisherController.expectNoMsg(timeout)
      }
    }
  }

}
