package de.htwg.mps.minesweeper.model.impl

import org.scalatest.WordSpec

class GridTest extends WordSpec {

  "A Grid" should {

    val grid = new Grid(10);

    "have width" in {
      assert(grid.width == 10)
    }

    "have height" in {
      assert(grid.height == 10)
    }

    "have number of bombs" in {
      assert(grid.bombs == 18)
    }

  }

}
