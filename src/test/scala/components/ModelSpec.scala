package components

import helpers.TestSpec
import components.Dsl._

class ModelSpec extends TestSpec {
  describe("Model") {
    it("should contain at least one constraint") {
      val objective = max(2("a"))
      val expectedError = the [IllegalArgumentException] thrownBy Model(Set.empty, objective)
      expectedError.getMessage should endWith("At least one constraint is required.")
    }

    it("should not contain terms in the objective that do not exist in the constraints") {
      val expectedError = the [IllegalArgumentException] thrownBy {
        max(1 ("a") + 2 ("b") - 3 ("c")) subjectTo(
          2 ("a") + 1 ("b") <= 3,
          1 ("a") - 2 ("b") == 4
        )
      }
      expectedError.getMessage should endWith("Unused variable in objective function.")
    }
  }

  describe("can be built from it's components") {
    it("case 1") {
      val constraints = Seq(
        2("a") - 3("b") == 3,
        1("a") + 2("b") <= 3
      )
      val objective = max(1("a") + 1("b"))

      val model = objective subjectTo(constraints: _*)
      model shouldEqual Model(constraints.toSet, objective)
    }
  }
}
