package de.htwg.mps.minesweeper.model.impl

import org.scalatest.WordSpec

class BombFieldTest extends WordSpec {

  "A BombField" should {

    val bombField = new BombField()

    "be a bomb" in {
      assert(bombField.isBomb)
    }

    "print a +" in {
      assert(bombField.toString == "+")
    }

  }



}