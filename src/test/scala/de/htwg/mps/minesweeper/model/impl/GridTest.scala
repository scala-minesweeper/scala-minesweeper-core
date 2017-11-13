package de.htwg.mps.minesweeper.model.impl

import org.scalatest.WordSpec

class GridTest extends WordSpec {

  "A Grid" should {

    val grid_10 = new Grid(10)

    "have width" in {
      assert(grid_10.width == 10)
    }

    "have height" in {
      assert(grid_10.height == 10)
    }

    "have number of bombs" in {
      assert(grid_10.bombs == 18)
    }

    "have a playground" in {
      assert(grid_10.playground.length==10)
    }
  }

  "B Grid" should {
    val grid_5_7_10 = new Grid(5,7,10)

    "have width" in {
      assert(grid_5_7_10.width == 5)
    }

    "have height" in {
      assert(grid_5_7_10.height == 7)
    }

    "have number of bombs" in {
      assert(grid_5_7_10.bombs == 10)
    }

    "have a playground" in {
      assert(grid_5_7_10.playground.length==5)
    }
  }

}
