package components

import components.Dsl._
import helpers.TestSpec

class ConstraintSpec extends TestSpec {
  describe("Constraint") {
    it("should contain at least one term") {
      val expectedError = the [IllegalArgumentException] thrownBy Constraint(Set.empty, EQ, 0)
      expectedError.getMessage should endWith("At least one term is required.")
    }

    it("should not contain terms with the same variable") {
      val leftHandSide = 1("a") + 2("b") + 2("a")
      val expectedError = the [IllegalArgumentException] thrownBy Constraint(leftHandSide, EQ, 0)
      expectedError.getMessage should endWith("Some variable appear multiple times.")
    }

    describe("can be built from it's components") {
      it("case 1") {
        val terms = Set(
          Term(2, Variable("a")),
          Term(-3, Variable("b"))
        )
        val constraint = 2("a") - 3("b") == 3
        constraint shouldEqual Constraint(terms, EQ, 3)
      }

      it("case 2") {
        val terms = Set(
          Term(-2, Variable("a")),
          Term(3, Variable("b"))
        )
        val constraint = -2("a") + 3("b") >= 5
        constraint shouldEqual Constraint(terms, GEQ, 5)
      }

      it("case 3") {
        val terms = Set(
          Term(-2, Variable("a")),
          Term(3, Variable("b"))
        )
        val constraint = -2("a") + 3("b") <= -5.0
        constraint shouldEqual Constraint(terms, LEQ, -5.0)
      }
    }
  }
}
