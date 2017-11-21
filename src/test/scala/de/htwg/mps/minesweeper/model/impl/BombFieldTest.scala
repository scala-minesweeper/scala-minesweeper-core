package de.htwg.mps.minesweeper.model.impl

import org.scalatest.WordSpec

class BombFieldTest extends WordSpec {

  "A shown BombField" should {

    val bombField: BombField = BombField().showField()

    "be a bomb" in {
      assert(bombField.isBomb)
    }

    "print a +" in {
      assert(bombField.toString == "+")
    }

    "is not flagged" in {
      assert(!bombField.isFlagged)
    }

    "is shown" in {
      assert(bombField.isShown)
    }


  }


}