package de.htwg.mps.minesweeper.model.impl

import org.scalatest.WordSpec

class NumberFieldTest extends WordSpec {

  "A hidden NumberField" should {
    val numberField = NumberField(0)

    "not be a bomb" in {
      assert(!numberField.isBomb)
    }

    "print a placeholder" in {
      assert(numberField.toString.equals("~"))
    }

    "not shown at start" in {
      assert(!numberField.isShown)
    }

    "is not flagged" in {
      assert(!numberField.isFlagged)
    }

    "is not marked with ?" in {
      assert(!numberField.isQuestionMarked)
    }
  }

  "An opened NumberField" should {
    val numberField: NumberField = NumberField(1).showField()

    "not be a bomb" in {
      assert(!numberField.isBomb)
    }

    "print a number" in {
      assert(numberField.toString.equals("1"))
    }

    "is shown" in {
      assert(numberField.isShown)
    }

    "is not flagged" in {
      assert(!numberField.isFlagged)
    }

    "is not marked with ?" in {
      assert(!numberField.isQuestionMarked)
    }
  }

  "An flagged NumberField" should {
    val numberField: NumberField = NumberField(1).flagField()

    "not be a bomb" in {
      assert(!numberField.isBomb)
    }

    "print a flag" in {
      assert(numberField.toString.equals("#"))
    }

    "is not shown at start" in {
      assert(!numberField.isShown)
    }

    "is flagged" in {
      assert(numberField.isFlagged)
    }

    "is not marked with ?" in {
      assert(!numberField.isQuestionMarked)
    }
  }

}
