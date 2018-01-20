package de.htwg.mps.minesweeper.core

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestProbe}
import de.htwg.mps.minesweeper.api.events.{GameModel, GameUpdate, GetCurrentStatus, PlayerUpdate}
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.duration._

class MinesweeperMainTest extends WordSpec with Matchers{

  "The main class for core " can {
    "start the core application and " should {
      "create the actor system and start the first game " in {
        val testKit = new TestKit(ActorSystem("MainTestingSystem"))
        implicit val system: ActorSystem = testKit.system
        val testProbeClient = TestProbe()

        MinesweeperMain.main(Array())

        MinesweeperMain.system.playerController.tell(GetCurrentStatus(), testProbeClient.ref)
        val currentPlayer = testProbeClient.expectMsgType[PlayerUpdate](2000.millis).player

        MinesweeperMain.system.gameController.tell(GetCurrentStatus(), testProbeClient.ref)
        val currentGame = testProbeClient.expectMsgType[GameUpdate](2000.millis).game

        currentPlayer.history.size shouldBe 0

        currentGame.running shouldBe true
        currentGame.gameResult shouldBe None
        currentGame.grid.size shouldBe (10, 10)
        currentGame.grid.bombs shouldBe 10

        testKit.shutdown()
      }
    }
  }

}
