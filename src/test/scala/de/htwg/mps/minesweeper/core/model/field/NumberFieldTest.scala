package de.htwg.mps.minesweeper.core.model.field

import org.scalatest.{Matchers, WordSpec}

class NumberFieldTest extends WordSpec with Matchers {

  "A hidden NumberField" should {
    val numberField = NumberField(0)

    "not be a bomb" in {
      numberField.isBomb shouldBe false
    }

    "print a placeholder" in {
      numberField.toString shouldBe "~"
    }

    "not shown at start" in {
      numberField.isShown shouldBe false
    }

    "is not flagged" in {
      numberField.isFlagged shouldBe false
    }

    "is not marked with ?" in {
      numberField.isQuestionMarked shouldBe false
    }
  }

  "An opened NumberField" should {
    val numberField: NumberField = NumberField(1).showField()

    "not be a bomb" in {
      numberField.isBomb shouldBe false
    }

    "print a number" in {
      numberField.toString shouldBe "1"
    }

    "is shown" in {
      numberField.isShown shouldBe true
    }

    "is not flagged" in {
      numberField.isFlagged shouldBe false
    }

    "is not marked with ?" in {
      numberField.isQuestionMarked shouldBe false
    }
  }

  "An flagged NumberField" should {
    val numberField: NumberField = NumberField(1).flagField()

    "not be a bomb" in {
      numberField.isBomb shouldBe false
    }

    "print a flag" in {
      numberField.toString shouldBe "#"
    }

    "is not shown at start" in {
      numberField.isShown shouldBe false
    }

    "is flagged" in {
      numberField.isFlagged shouldBe true
    }

    "is not marked with ?" in {
      numberField.isQuestionMarked shouldBe false
    }
  }

}
