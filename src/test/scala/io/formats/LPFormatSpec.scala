package io.formats

import components.Dsl._
import components.{Constraint, Objective}
import helpers.TestSpec

class LPFormatSpec extends TestSpec {
  describe("LPFormat") {
    it("should return a LP string representation of a model") {
      val modelObjective = max(-1("a") + 2("b") - 1.5("c"))
      val modelConstraints = Set(
        1("a") + 3("b") <= 5,
        3("a") - 1("b") == 0,
        1("c") <= 10
      )

      val expectedLpString =
        s"""
           |max: -a + 2b - 1.5c;
           |a + 3b <= 5;
           |3a - b = 0;
           |c <= 10;
           |a >= 0;
           |a <= 1;
           |b >= 0;
           |b <= 1;
           |c >= 0;
           |c <= 1;
           |int a b c;""".stripMargin.trim

      val formatter = new LPFormat {
        override val constraints: Set[Constraint] = modelConstraints
        override val objective: Objective = modelObjective
      }

      // TODO fix issue with line seperator and multi-line strings here
      formatter.toLpString.diff(expectedLpString).trim shouldEqual ""
    }
  }
}
