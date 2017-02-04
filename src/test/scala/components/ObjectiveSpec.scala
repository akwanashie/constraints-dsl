package components

import components.Dsl._
import helpers.TestSpec

class ObjectiveSpec extends TestSpec {
  describe("Objective") {
    it("should contain at least one term") {
      val expectedError = the [IllegalArgumentException] thrownBy Objective(Set.empty, MAX)
      expectedError.getMessage should endWith("At least one term is required.")
    }

    it("should not contain terms with the same variable") {
      val terms = 1("a") + 2("b") + 2("a")
      val expectedError = the [IllegalArgumentException] thrownBy Objective(terms, MAX)
      expectedError.getMessage should endWith("Some variable appear multiple times.")
    }
  }

  describe("can be built from it's components") {
    it("case 1") {
      val terms = Set(
        Term(2, Variable("a")),
        Term(-3, Variable("b"))
      )

      val objective = max(2 ("a") - 3 ("b"))
      objective shouldEqual Objective(terms, MAX)
    }

    it("case 2") {
      val terms = Set(
        Term(2, Variable("a")),
        Term(-3, Variable("b"))
      )

      val objective = min(2 ("a") - 3 ("b"))
      objective shouldEqual Objective(terms, MIN)
    }
  }
}
