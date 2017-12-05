package de.htwg.mps.minesweeper.view.gui

import scala.swing.TextField
import scala.swing.event.KeyTyped

/**
  * A scala swing component, which only allows number input.
  */
class DigitTextField extends TextField {
  verifier = numberVerifier
  listenTo(keys)
  reactions += {
    case e: KeyTyped =>
      if (!e.char.isDigit)
        e.consume
  }

  def value: Int = text.toInt

  private def numberVerifier(text: String): Boolean = text.forall(_.isDigit)
}
