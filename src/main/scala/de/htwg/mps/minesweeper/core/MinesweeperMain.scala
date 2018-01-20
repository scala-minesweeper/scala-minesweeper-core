package de.htwg.mps.minesweeper.core


import com.typesafe.config.ConfigFactory
import de.htwg.mps.minesweeper.api.events.StartGame

object MinesweeperMain {

  val system = ActorSystemCreator(ConfigFactory.load())

  def main(args: Array[String]) {
    system.gameController ! StartGame(10, 10, 10)
  }

}
