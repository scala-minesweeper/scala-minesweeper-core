package de.htwg.mps.minesweeper.model.field

import org.scalatest.{Matchers, WordSpec}

class BombFieldTest extends WordSpec with Matchers {

  "A shown BombField" should {

    val bombField: BombField = BombField().showField()

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


  }


}