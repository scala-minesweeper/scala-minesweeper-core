package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IField
import org.scalatest.WordSpec

import scala.util.Random

class GridTest extends WordSpec {

  "A 10x10 Grid" should {

    val grid_10 = Grid(TwoDimensionalArray[IField](10, 10, NumberField(0)), 18, Random)
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
    val grid_5_7_10 = Grid(TwoDimensionalArray[IField](5, 7, NumberField(0)), 10, Random)
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

  "An initialized 3x3 Grid with 2 bombs and fixed random" should {
    val grid = new Grid(TwoDimensionalArray[IField](3, 3, NumberField(0)), 2, new Random(5))
      .init()

    println(grid.toString)

    "have bombs" in {
      assert(grid.playground.get(0, 0).get.isBomb)
      assert(grid.playground.get(1, 2).get.isBomb)
    }

    "have numbers" in {
      assert(grid.playground.get(0, 1).get.asInstanceOf[NumberField].numberBombs == 2)
      assert(grid.playground.get(0, 2).get.asInstanceOf[NumberField].numberBombs == 1)
      assert(grid.playground.get(1, 0).get.asInstanceOf[NumberField].numberBombs == 1)
      assert(grid.playground.get(1, 1).get.asInstanceOf[NumberField].numberBombs == 2)
      assert(grid.playground.get(2, 0).get.asInstanceOf[NumberField].numberBombs == 0)
      assert(grid.playground.get(2, 1).get.asInstanceOf[NumberField].numberBombs == 1)
      assert(grid.playground.get(2, 2).get.asInstanceOf[NumberField].numberBombs == 1)
    }

    "print a grid" in {
      val result =
        "  | 0 1 2\n" +
          "--|------\n" +
          "0 | ~ ~ ~\n" +
          "1 | ~ ~ ~\n" +
          "2 | ~ ~ ~\n"
      assert(grid.toString == result)
    }

 }

}
