package de.htwg.mps.minesweeper.model.impl

import org.scalatest.WordSpec

class BombFieldTest extends WordSpec {

  "A BombField" should {

    val bombField = BombField()

    "be a bomb" in {
      assert(bombField.isBomb)
    }

    "print a +" in {
      assert(bombField.toString == "+")
    }

    "is not flagged" in {
      assert(!bombField.isFlagged)
    }

    "is not shown" in {
      assert(!bombField.isShown)
    }



  }



}