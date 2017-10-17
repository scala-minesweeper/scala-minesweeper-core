package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IGrid

class Grid(val width: Int, val height: Int, val bombs: Int) extends IGrid {
  // TODO bomb calculation
  def this(value: Int) = this(value, value, (value-1)*2)

  //var playground:Array[Array[Field]]
}
