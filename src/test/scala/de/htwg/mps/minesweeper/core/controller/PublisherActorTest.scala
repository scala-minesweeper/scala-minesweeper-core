package de.htwg.mps.minesweeper.core.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import de.htwg.mps.minesweeper.api.events._
import org.scalatest._

import scala.concurrent.duration._
import scala.language.postfixOps

class PublisherActorTest extends TestKit(ActorSystem("TestSystem"))
  with Matchers with FlatSpecLike with BeforeAndAfterAll {

  val timeout: FiniteDuration = 2000.millis
  val config = ConfigFactory.load()


  override def afterAll: Unit = {
    shutdown(system)
  }

  "A publisher actor" should "return" in {

    val testProbe = TestProbe()

    val publisher: ActorRef =
      system.actorOf(Props[PublisherActor])




    publisher.tell(RegisterObserver(), testProbe.ref)


    publisher.tell(DeregisterObserver(), testProbe.ref)

  }

}
