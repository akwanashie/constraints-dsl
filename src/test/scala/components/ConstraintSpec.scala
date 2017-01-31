package components

import components.Dsl._
import helpers.TestSpec

class ConstraintSpec extends TestSpec {
  describe("Constraint") {
    it("should contain at least one variable") {
      val expectedError = the [IllegalArgumentException] thrownBy Constraint(Set.empty, EQ, 0)
      expectedError.getMessage should endWith("Constraints must contain at least one term.")
    }

    it("should not contain terms with the same variable") {
      val lhs: Set[Term] = Set(1("a"), 2("b"), 2("a"))
      val expectedError = the [IllegalArgumentException] thrownBy Constraint(lhs, EQ, 0)
      expectedError.getMessage should endWith("Some variable appear multiple times.")
    }
  }
}
