package de.htwg.mps.minesweeper.model

trait IGrid {
  def height:Int
  def width:Int
  def bombs:Int
//  def playground:Array[Array[IField]]
}
