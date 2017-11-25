package de.htwg.mps.minesweeper.model.field

import org.scalatest.{Matchers, WordSpec}

class BombFieldTest extends WordSpec with Matchers {

  "A hidden BombField" should {
    val bombField: BombField = BombField(FieldHiddenState())

    "be a bomb" in {
      bombField.isBomb shouldBe true
    }
    "print a ~" in {
      bombField.toString shouldBe "~ "
    }
    "is not flagged" in {
      bombField.isFlagged shouldBe false
    }
    "is not shown" in {
      bombField.isShown shouldBe false
    }
    "is not ? marked" in {
      bombField.isQuestionMarked shouldBe false
    }
    "has state hidden" in {
      bombField.fieldState shouldBe FieldHiddenState()
    }
  }

  "A opened BombField" should {
    val bombField: BombField = BombField(FieldOpenState())

    "be a bomb" in {
      bombField.isBomb shouldBe true
    }
    "print a +" in {
      bombField.toString shouldBe "+ "
    }
    "is not flagged" in {
      bombField.isFlagged shouldBe false
    }
    "is shown" in {
      bombField.isShown shouldBe true
    }
    "is not ? marked" in {
      bombField.isQuestionMarked shouldBe false
    }
    "has state open" in {
      bombField.fieldState shouldBe FieldOpenState()
    }
  }

  "A flagged BombField" should {
    val bombField: BombField = BombField(FieldFlaggedState())

    "be a bomb" in {
      bombField.isBomb shouldBe true
    }
    "print a #" in {
      bombField.toString shouldBe "# "
    }
    "is flagged" in {
      bombField.isFlagged shouldBe true
    }
    "is not shown" in {
      bombField.isShown shouldBe false
    }
    "is not ? marked" in {
      bombField.isQuestionMarked shouldBe false
    }
    "has state flagged" in {
      bombField.fieldState shouldBe FieldFlaggedState()
    }
  }

  "A ? marked BombField" should {
    val bombField: BombField = BombField(FieldQuestionMarkedState())

    "be a bomb" in {
      bombField.isBomb shouldBe true
    }
    "print a ?" in {
      bombField.toString shouldBe "? "
    }
    "is not flagged" in {
      bombField.isFlagged shouldBe false
    }
    "is not shown" in {
      bombField.isShown shouldBe false
    }
    "is ? marked" in {
      bombField.isQuestionMarked shouldBe true
    }
    "has state flagged" in {
      bombField.fieldState shouldBe FieldQuestionMarkedState()
    }
  }

  "A hidden BombField" should {
    val bombField: BombField = BombField(FieldHiddenState())

    "be opened after call open" in {
      bombField.showField().isShown shouldBe true
    }

    "be ? marked after set marked call" in {
      bombField.questionField().isQuestionMarked shouldBe true
    }

    "be # flagged after flagged call" in {
      bombField.flagField().isFlagged shouldBe true
    }

    "be # flagged after toggled call" in {
      bombField.toggleNextFieldState().isFlagged shouldBe true
    }

    "be ? marked after two toggled call" in {
      bombField.toggleNextFieldState().toggleNextFieldState().isQuestionMarked shouldBe true
    }

  }

  "The BombField object" should {

    "return a hidden BombField" in {
      BombField() shouldEqual BombField(FieldHiddenState())
    }

  }

}