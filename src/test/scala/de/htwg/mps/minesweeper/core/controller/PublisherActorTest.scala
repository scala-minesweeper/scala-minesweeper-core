package de.htwg.mps.minesweeper.core.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{TestKit, TestProbe}
import de.htwg.mps.minesweeper.api.events._
import de.htwg.mps.minesweeper.core.model.player.MinesweeperPlayer
import org.scalatest.{Matchers, Outcome, fixture}

import scala.concurrent.duration._

class PublisherActorTest extends fixture.WordSpec with Matchers {

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


  "A publisher actor " can {
    "register an observer and " should {
      "publish events to this observer " in {
        system => {
          val testObserver = TestProbe()(system)
          val testPublisher = TestProbe()(system)

          val publisherController: ActorRef = system.actorOf(Props(new PublisherActor()))

          publisherController.tell(RegisterPublisher, testPublisher.ref)
          publisherController.tell(RegisterObserver, testObserver.ref)

          testPublisher.expectMsg(timeout, GetCurrentStatus())

          // send test event
          publisherController.tell(PlayerUpdate(MinesweeperPlayer()), testPublisher.ref)

          testObserver.expectMsg(PlayerUpdate(MinesweeperPlayer()))

          testObserver.expectNoMsg(timeout)
          testPublisher.expectNoMsg(timeout)

        }
      }
    }

    "deregister an observer and " should {
      "do not publish events to this observer " in {
        system => {
          val testObserver = TestProbe()(system)
          val testPublisher = TestProbe()(system)

          val publisherController: ActorRef = system.actorOf(Props(new PublisherActor()))

          publisherController.tell(RegisterPublisher, testPublisher.ref)
          publisherController.tell(RegisterObserver, testObserver.ref)

          testPublisher.expectMsg(timeout, GetCurrentStatus())

          // send test event
          publisherController.tell(PlayerUpdate(MinesweeperPlayer()), testPublisher.ref)

          testObserver.expectMsg(PlayerUpdate(MinesweeperPlayer()))


          publisherController.tell(DeregisterObserver, testObserver.ref)
          publisherController.tell(DeregisterPublisher, testPublisher.ref)
          testObserver.expectNoMsg(timeout)
          testPublisher.expectNoMsg(timeout)

        }
      }
    }
  }

}
