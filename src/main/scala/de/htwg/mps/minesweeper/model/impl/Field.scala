package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IField

class Field extends IField {
  var isShown = false
  var isFlagged = false

  def showField: Unit = isShown = true
  def flagField: Unit = isFlagged = true
  def unflagField: Unit = isFlagged = false
}
