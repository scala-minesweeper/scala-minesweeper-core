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

  "A NumberField" should {
    val numberField = new NumberField()

    "be not a bomb" in {
      assert(!numberField.isBomb)
    }

    "print a number" in {
      assert(numberField.toString.matches("[0-8]"))
    }
  }

}