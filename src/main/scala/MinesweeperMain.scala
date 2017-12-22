
import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import de.htwg.mps.minesweeper.controller.{GameControllerImpl, GameControllerPublisher, StartGame}
import de.htwg.mps.minesweeper.view.tui.{ProcessTuiInput, Tui}

import scala.io.StdIn

object MinesweeperMain {

  private val config = ConfigFactory.load()
  private val actorSystemName = config.getString("akka.minesweeper.systemName")
  private val controllerActorName = config.getString("akka.minesweeper.controllerActor")
  private val publisherActorName = config.getString("akka.minesweeper.publisherActor")

  val system = ActorSystem(actorSystemName)

  val publisher: ActorRef =
    system.actorOf(Props[GameControllerPublisher], publisherActorName)

  val controller: ActorRef =
    system.actorOf(Props(new GameControllerImpl(publisher)), controllerActorName)

  val tui: ActorRef =
    system.actorOf(Props(new Tui(controller, publisher)))

  //val gui = new SwingGui(controller)

  def main(args: Array[String]) {
    controller.tell(StartGame(5, 5, 10), null)
    while (true) {
      tui.tell(ProcessTuiInput(StdIn.readLine()), null)
    }
  }
}
