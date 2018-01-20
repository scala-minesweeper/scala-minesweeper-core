package de.htwg.mps.minesweeper.core.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{TestKit, TestProbe}
import de.htwg.mps.minesweeper.api.{FieldOpenState, GameResult}
import de.htwg.mps.minesweeper.api.events._
import de.htwg.mps.minesweeper.core.model.player.MinesweeperPlayer
import org.scalatest.{Matchers, Outcome, fixture}

import scala.concurrent.duration._
import scala.util.Random

class PlayerControllerActorTest extends fixture.WordSpec with Matchers {

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


  "A player controller actor " can {
    "manage the score history entry and " should {
      "add a score if a game is won " in {
        system => {
          val testPublisher = TestProbe()(system)

          val player = MinesweeperPlayer()

          val playerController: ActorRef = system.actorOf(Props(new PlayerControllerActor(testPublisher.ref, player)))

          playerController ! GameWon(GameModel(finished = true, running = false,
            Some(GameResult(win = true, 0, 0, 1, 1)),
            GridModel(0, 0, (1, 1), List(List(FieldModel(FieldOpenState, "0"))))))

          testPublisher.expectMsg(timeout, RegisterPublisher)
          testPublisher.expectMsg(timeout, PlayerUpdate(PlayerModel(List(GameResult(win = true, 0, 0, 1, 1)))))
        }
      }

      "add a score if a game is lost " in {
        system => {
          val testPublisher = TestProbe()(system)

          val player = MinesweeperPlayer()

          val playerController: ActorRef = system.actorOf(Props(new PlayerControllerActor(testPublisher.ref, player)))

          playerController ! GameLost(GameModel(finished = true, running = false,
            Some(GameResult(win = false, 0, 1, 1, 1)),
            GridModel(1, 0, (1, 1), List(List(FieldModel(FieldOpenState, "+"))))))

          testPublisher.expectMsg(timeout, RegisterPublisher)
          testPublisher.expectMsg(timeout, PlayerUpdate(PlayerModel(List(GameResult(win = false, 0, 1, 1, 1)))))
        }
      }
    }

    "return the current status and " should {
      "send a player update message " in {
        system => {
          val testPublisher = TestProbe()(system)
          val testObserver = TestProbe()(system)

          val player = MinesweeperPlayer()

          val playerController: ActorRef = system.actorOf(Props(new PlayerControllerActor(testPublisher.ref, player)))

          playerController.tell(GetCurrentStatus(), testObserver.ref)

          testPublisher.expectMsg(timeout, RegisterPublisher)
          testObserver.expectMsg(timeout, PlayerUpdate(PlayerModel(List())))
        }
      }
    }
  }

}
