package de.htwg.mps.minesweeper.model.grid

import de.htwg.mps.minesweeper.model.field.{Field, NumberField}
import org.scalatest.{Matchers, WordSpec}

import scala.util.Random

class MinesweeperGridTest extends WordSpec with Matchers {

  "A 10x10 Grid" should {

    val grid_10 = MinesweeperGrid(TwoDimensionalArray[Field](10, 10, NumberField(0)), 18, Random)
    grid_10.init()

    "have width" in {
      grid_10.playground.cols shouldBe 10
    }

    "have height" in {
      grid_10.playground.rows shouldBe 10
    }

    "have number of bombs" in {
      grid_10.bombs shouldBe 18
    }

  }

  "A 5x7 Grid with 10 bombs" should {
    val grid_5_7_10 = MinesweeperGrid(TwoDimensionalArray[Field](5, 7, NumberField(0)), 10, Random)
    grid_5_7_10.init()

    "have width" in {
      grid_5_7_10.playground.rows shouldBe 5
    }

    "have height" in {
      grid_5_7_10.playground.cols shouldBe 7
    }

    "have number of bombs" in {
      grid_5_7_10.bombs shouldBe 10
    }

  }

  "An initialized 3x3 Grid with 2 bombs and fixed random" should {
    val grid = new MinesweeperGrid(TwoDimensionalArray[Field](3, 3, NumberField(0)), 2, new Random(5))
      .init()

    "have bombs" in {
      grid.playground.get(0, 0).get.isBomb shouldBe true
      grid.playground.get(1, 2).get.isBomb shouldBe true
    }

    "have numbers" in {
      grid.playground.get(0, 1).get.asInstanceOf[NumberField].numberBombs shouldBe 2
      grid.playground.get(0, 2).get.asInstanceOf[NumberField].numberBombs shouldBe 1
      grid.playground.get(1, 0).get.asInstanceOf[NumberField].numberBombs shouldBe 1
      grid.playground.get(1, 1).get.asInstanceOf[NumberField].numberBombs shouldBe 2
      grid.playground.get(2, 0).get.asInstanceOf[NumberField].numberBombs shouldBe 0
      grid.playground.get(2, 1).get.asInstanceOf[NumberField].numberBombs shouldBe 1
      grid.playground.get(2, 2).get.asInstanceOf[NumberField].numberBombs shouldBe 1
    }

    "print a grid" in {
      val result =
        "   | 0  1  2\n" +
          "---|---------\n" +
          "0  | ~  ~  ~ \n" +
          "1  | ~  ~  ~ \n" +
          "2  | ~  ~  ~ \n"
      grid.toString shouldBe result
    }

  }

}
