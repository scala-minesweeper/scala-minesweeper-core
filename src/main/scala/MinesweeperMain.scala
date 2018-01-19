
import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import de.htwg.mps.minesweeper.api.events.StartGame
import de.htwg.mps.minesweeper.core.controller.{GameControllerActor, PlayerControllerActor, PublisherActor}

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

  def main(args: Array[String]) {
    gameController.tell(StartGame(5, 5, 10), gameController)
  }
}
