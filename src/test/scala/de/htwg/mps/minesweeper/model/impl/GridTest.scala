package de.htwg.mps.minesweeper.model.impl

import org.scalatest.WordSpec

class GridTest extends WordSpec {

  "A 10x10 Grid" should {

    val grid_10 = Grid(10, 10, 18)
    grid_10.init()

    "have width" in {
      assert(grid_10.playground.cols == 10)
    }

    "have height" in {
      assert(grid_10.playground.rows == 10)
    }

    "have number of bombs" in {
      assert(grid_10.bombs == 18)
    }

  }

  "A 5x7 Grid with 10 bombs" should {
    val grid_5_7_10 = Grid(5, 7, 10)
    grid_5_7_10.init()

    "have width" in {
      assert(grid_5_7_10.playground.rows == 5)
    }

    "have height" in {
      assert(grid_5_7_10.playground.cols == 7)
    }

    "have number of bombs" in {
      assert(grid_5_7_10.bombs == 10)
    }

  }
}
