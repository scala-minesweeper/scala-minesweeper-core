package de.htwg.mps.minesweeper.model

trait IGame {
  def grid: IGrid

  def score: Int
}
