package de.htwg.mps.minesweeper.core

import org.scalatest.WordSpec

class MinesweeperMainTest extends WordSpec {

  "The minesweeper main " can {
    "start the core application and " should {
      "create the publisher and game/player controller actors and start a game " in {
        val system = MinesweeperMain.system
        val publisherActor = MinesweeperMain.publisher
        val playerControllerActor = MinesweeperMain.playerController
        val gameControllerActor = MinesweeperMain.gameController

        MinesweeperMain.main(Array())
      }
    }
  }

}
