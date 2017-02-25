package components

import helpers.TestSpec
import components.Dsl._
import io.handlers.IOHandler
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar.mock
import org.scalatest.MustMatchers

import scala.util.Random

class ModelSpec extends TestSpec {
  describe("Model") {
    it("should contain at least one constraint") {
      val objective = max(2("a"))
      val expectedError = the [IllegalArgumentException] thrownBy Model(Set.empty, Some(objective))
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

    it("should allow new constraints to be added") {
      val model = max(1("a") + 2("b")) subjectTo (
          1("a") + 3("b") <= 5,
          3("a") - 1("b") == 0
        )
      val newModel = model + (1("a") - 5("b") >= 0)
      val expectedModel = max(1("a") + 2("b")) subjectTo (
          1("a") + 3("b") <= 5,
          3("a") - 1("b") == 0,
          1("a") - 5("b") >= 0
        )

      newModel shouldEqual expectedModel
    }

    it("should return all the variables used") {
      val model = max(1("a") + 2("b")) subjectTo (
        1("a") + 3("b") + 1("c") <= 5,
        3("a") - 1("b") == 0,
        1("c") <= 10
        )
      val expectedVariables: Set[Variable] = Set("a", "b", "c")
      model.variables shouldEqual expectedVariables
    }

    it("should allow for the objective function to be modified") {
      val model = max(1("a") + 2("b")) subjectTo (
          1("a") + 3("b") <= 5,
          3("a") - 1("b") == 0
        )
      val newObjective = min(1("a") - 2("b"))
      val newModel = model withObjective newObjective
      val expectedModel = min(1("a") - 2("b")) subjectTo (
          1("a") + 3("b") <= 5,
          3("a") - 1("b") == 0
        )

      newModel shouldEqual expectedModel
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
      model shouldEqual Model(constraints.toSet, Some(objective))
    }
  }
}
