package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IField

case class BombField(isShown: Boolean, isFlagged: Boolean) extends IField {

  override def showField(): IField = copy(isShown = true)

  override def flagField(): IField = copy(isFlagged = true)

  override def unflagField(): IField = copy(isFlagged = false)

  override def isBomb: Boolean = true

  override def toString: String = if(isShown) "+" else hiddenFieldString

}

object BombField {
  def apply(): BombField = new BombField(false, false)
}
