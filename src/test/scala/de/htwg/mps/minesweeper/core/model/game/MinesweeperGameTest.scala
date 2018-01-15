package de.htwg.mps.minesweeper.core.model.game


import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import de.htwg.mps.minesweeper.api.{Field, FieldHiddenState, FieldOpenState, GameResult}

import scala.concurrent.duration._
import de.htwg.mps.minesweeper.api.events._
import de.htwg.mps.minesweeper.core.controller.{GameControllerActor, PlayerControllerActor, PublisherActor}
import de.htwg.mps.minesweeper.view.gui.GameLostEvent
import org.scalatest._

class MinesweeperGameTest(_system: ActorSystem) extends TestKit(_system) with Matchers with FlatSpecLike with BeforeAndAfterAll {


  def this() = this(ActorSystem("minesweeper"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "A 1x1 Grid" should
    "do something" in {


    val config = ConfigFactory.load()
    val controllerActorName = ConfigFactory.load().getString("akka.minesweeper.controllerActor")

    val publisherActorName = config.getString("akka.minesweeper.publisherActor")

    val testProbePlayerController = TestProbe()

    val publisher: ActorRef =
      system.actorOf(Props[PublisherActor], publisherActorName)
    val playerController: ActorRef =
      system.actorOf(Props(new PlayerControllerActor(publisher)))
    val gameController: ActorRef =
      system.actorOf(Props(new GameControllerActor(testProbePlayerController.ref, playerController)), controllerActorName)



    gameController ! StartGame(1,1,1)
    gameController ! OpenField(0,0)
    testProbePlayerController.expectMsg(500 millis, RegisterPublisher)
    testProbePlayerController.expectMsg(500 millis, GameStart(GameModel(false,true,None,GridModel(1,1,(1,1),List(List(FieldModel(FieldHiddenState,"~")))))))
    testProbePlayerController.expectMsg(500 millis, FieldUpdate(0,0,FieldModel(FieldOpenState,"+"),GridModel(1,0,(1,1),List(List(FieldModel(FieldOpenState,"+"))))))
    testProbePlayerController.expectMsg(500 millis, GridUpdate(GridModel(1,0,(1,1),List(List(FieldModel(FieldOpenState,"+"))))))
    //TODO fixen

    testProbePlayerController.expectMsg(500 millis, GameLost(new GameModel(false,false,Some(GameResult(false, 0,1,0,1)) ,GridModel(1,0,(1,1),List(List(FieldModel(FieldOpenState,"+")))))))


  }

}
