package de.htwg.mps.minesweeper.core.model.game

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import de.htwg.mps.minesweeper.api._
import de.htwg.mps.minesweeper.api.events._
import de.htwg.mps.minesweeper.core.controller.{GameControllerActor, PlayerControllerActor, PublisherActor}
import org.scalatest._

import scala.concurrent.duration._
import scala.language.postfixOps

class MinesweeperGameOpenNumberFieldWinTest extends TestKit(ActorSystem("TestSystem"))
  with Matchers with FlatSpecLike with BeforeAndAfterAll {

  override def afterAll: Unit = {
    shutdown(system)
  }

  "A 1x1 Grid" should "game win" in {

      val testProbePlayerController = TestProbe()

      val publisher: ActorRef =
        system.actorOf(Props[PublisherActor])
      val playerController: ActorRef =
        system.actorOf(Props(new PlayerControllerActor(publisher)))
      val gameController: ActorRef =
        system.actorOf(Props(new GameControllerActor(testProbePlayerController.ref, playerController)))

      gameController ! StartGame(1,1,0)
      gameController ! OpenField(0,0)
      testProbePlayerController.expectMsg(500 millis, RegisterPublisher)
      testProbePlayerController.expectMsg(500 millis, GameStart(GameModel(finished = false,running = true,None,GridModel(0,0,(1,1),List(List(FieldModel(FieldHiddenState,"~")))))))
      testProbePlayerController.expectMsg(500 millis, GridUpdate(GridModel(0, 0, (1, 1), List(List(FieldModel(FieldOpenState, "0"))))))
      //testProbePlayerController.expectMsg(500 millis, FieldUpdate(0, 0, FieldModel(FieldOpenState, "0"), GridModel(0, 0, (1, 1), List(List(FieldModel(FieldOpenState, "0"))))))

      testProbePlayerController.expectMsg(500 millis, GameWon(new GameModel(false,false,Some(GameResult(win = true, 0,0,0,1)) ,GridModel(0,0,(1,1),List(List(FieldModel(FieldOpenState,"0")))))))

  }

}
