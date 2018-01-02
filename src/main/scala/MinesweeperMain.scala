
import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import de.htwg.mps.minesweeper.controller.{GameControllerActor, PlayerControllerActor, PublisherActor, StartGame}
import de.htwg.mps.minesweeper.view.gui.SwingGuiActor
import de.htwg.mps.minesweeper.view.tui.{ProcessTuiInput, TuiActor}

import scala.io.StdIn

object MinesweeperMain {

  private val config = ConfigFactory.load()
  private val actorSystemName = config.getString("akka.minesweeper.systemName")
  private val controllerActorName = config.getString("akka.minesweeper.controllerActor")
  private val publisherActorName = config.getString("akka.minesweeper.publisherActor")

  val system = ActorSystem(actorSystemName)

  val publisher: ActorRef =
    system.actorOf(Props[PublisherActor], publisherActorName)

  val playerController: ActorRef =
    system.actorOf(Props(new PlayerControllerActor(publisher)))
  val gameController: ActorRef =
    system.actorOf(Props(new GameControllerActor(publisher, playerController)), controllerActorName)

  val tui: ActorRef =
    system.actorOf(Props(new TuiActor(gameController, publisher)))

  val gui: ActorRef =
    system.actorOf(Props(new SwingGuiActor(gameController, publisher)))

  def main(args: Array[String]) {
    gameController.tell(StartGame(5, 5, 10), gameController)
    while (true) {
      tui.tell(ProcessTuiInput(StdIn.readLine()), tui)
    }
  }
}
