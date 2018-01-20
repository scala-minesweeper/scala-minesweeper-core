package de.htwg.mps.minesweeper.core

import com.typesafe.config.ConfigFactory
import org.scalatest.{Matchers, WordSpec}

class ActorSystemCreatorTest extends WordSpec with Matchers {

  "The minesweeper system creator " can {
    "setup the core application and " should {
      "create the publisher and game/player controller actors " in {
        val creator = ActorSystemCreator(ConfigFactory.load("application.conf"))
        val system = creator.system
        val publisherActor = creator.publisher
        val gameControllerActor = creator.gameController

        // test the public actors
        system.name shouldBe "minesweeperTest"
        publisherActor.path.toString shouldBe "akka://minesweeperTest/user/publisherTest"
        gameControllerActor.path.toString shouldBe "akka://minesweeperTest/user/controllerTest"
      }
    }
  }

}
