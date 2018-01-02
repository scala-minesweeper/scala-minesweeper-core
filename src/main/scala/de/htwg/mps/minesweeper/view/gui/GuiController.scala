package de.htwg.mps.minesweeper.view.gui

import akka.actor.ActorRef
import de.htwg.mps.minesweeper.core.controller.{GameEvent, OpenField, StartGame, ToggleField}

import scala.swing.Publisher

class GuiController(gameController: ActorRef) extends Publisher {

  def restartGame(row: Int, col: Int, bombs: Int): Unit = gameController ! StartGame(row, col, bombs)

  def toggleMarkField(row: Int, col: Int): Unit = gameController ! ToggleField(row, col)

  def openField(row: Int, col: Int): Unit = gameController ! OpenField(row, col)

  def sendEvent(event: GameEvent): Unit = publish(event)

}
