package de.htwg.mps.minesweeper.model.impl

import org.scalatest.WordSpec

class NumberFieldTest extends WordSpec {

  "A NumberField" should {
    val numberField = NumberField(0)

    "be not a bomb" in {
      assert(!numberField.isBomb)
    }

    "print a number at start" in {
      assert(numberField.toString.equals("0"))
    }

    "not shown at start" in {
      assert(!numberField.isShown)
    }

    "is not flagged" in {
      assert(!numberField.isFlagged)
    }
  }

}
