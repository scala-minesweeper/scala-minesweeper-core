package de.htwg.mps.minesweeper.utils

import org.scalatest.{Matchers, WordSpec}

class NumberUtilsTest extends WordSpec with Matchers {

  "The NumberUtils number of digits calculation method" should {

    "return 0 for 0" in {
      NumberUtils.numberOfDigits(0) shouldBe 0
    }

    "return 1 for x > 0 and x < 10" in {
      NumberUtils.numberOfDigits(1) shouldBe 1
      NumberUtils.numberOfDigits(2) shouldBe 1
      NumberUtils.numberOfDigits(5) shouldBe 1
      NumberUtils.numberOfDigits(8) shouldBe 1
      NumberUtils.numberOfDigits(9) shouldBe 1
    }

    "return 1 for x < 0 and x > -10" in {
      NumberUtils.numberOfDigits(-1) shouldBe 1
      NumberUtils.numberOfDigits(-2) shouldBe 1
      NumberUtils.numberOfDigits(-5) shouldBe 1
      NumberUtils.numberOfDigits(-8) shouldBe 1
      NumberUtils.numberOfDigits(-9) shouldBe 1
    }

    "return 2 for x >= 10 and x < 100" in {
      NumberUtils.numberOfDigits(10) shouldBe 2
      NumberUtils.numberOfDigits(21) shouldBe 2
      NumberUtils.numberOfDigits(55) shouldBe 2
      NumberUtils.numberOfDigits(87) shouldBe 2
      NumberUtils.numberOfDigits(99) shouldBe 2
    }

    "return 2 for x <= -10 and x > -100" in {
      NumberUtils.numberOfDigits(-11) shouldBe 2
      NumberUtils.numberOfDigits(-20) shouldBe 2
      NumberUtils.numberOfDigits(-54) shouldBe 2
      NumberUtils.numberOfDigits(-83) shouldBe 2
      NumberUtils.numberOfDigits(-99) shouldBe 2
    }

    "return 3 for x <= -100 and x > -1000" in {
      NumberUtils.numberOfDigits(-100) shouldBe 3
      NumberUtils.numberOfDigits(-999) shouldBe 3
    }

    "return 3 for x >= 100 and x < 1000" in {
      NumberUtils.numberOfDigits(100) shouldBe 3
      NumberUtils.numberOfDigits(999) shouldBe 3
    }

    "return 4 for x <= -1000 and x > -10000" in {
      NumberUtils.numberOfDigits(-1000) shouldBe 4
      NumberUtils.numberOfDigits(-9999) shouldBe 4
    }

    "return 4 for x >= 1000 and x < 10000" in {
      NumberUtils.numberOfDigits(1000) shouldBe 4
      NumberUtils.numberOfDigits(9999) shouldBe 4
    }

    "return 5 for x <= -10000 and x > -100000" in {
      NumberUtils.numberOfDigits(-10000) shouldBe 5
      NumberUtils.numberOfDigits(-99999) shouldBe 5
    }

    "return 5 for x >= 10000 and x < 100000" in {
      NumberUtils.numberOfDigits(10000) shouldBe 5
      NumberUtils.numberOfDigits(99999) shouldBe 5
    }

  }

}
