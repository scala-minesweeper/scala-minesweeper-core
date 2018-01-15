package de.htwg.mps.minesweeper.core.utils

import org.scalatest.{Matchers, WordSpec}

class NumberToStringUtilsTest extends WordSpec with Matchers {

  "The StringUtils for char at position in a string representation of a number " should {

    "return the char 1, 0 and 0 at position 0, 1 and 2 " in {
      val input = 100
      NumberToStringUtils.getCharStringAtOrElse(input, 0, "e") shouldBe "1"
      NumberToStringUtils.getCharStringAtOrElse(input, 1, "e") shouldBe "0"
      NumberToStringUtils.getCharStringAtOrElse(input, 2, "e") shouldBe "0"
    }

    "return the orElse char for positions out of range " in {
      val input = 100
      NumberToStringUtils.getCharStringAtOrElse(input, -1, "e") shouldBe "e"
      NumberToStringUtils.getCharStringAtOrElse(input, 3, "e") shouldBe "e"
      NumberToStringUtils.getCharStringAtOrElse(input, 4, "e") shouldBe "e"
    }

  }

  "The StringUtils method to calculate the length of a number in string representation " should {

    "return length 1 for 9 and 2 for 37 and 3 for 103 " in {
      NumberToStringUtils.stringLength(9) shouldBe 1
      NumberToStringUtils.stringLength(37) shouldBe 2
      NumberToStringUtils.stringLength(103) shouldBe 3
    }

  }

}
