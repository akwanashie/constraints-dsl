package components

import helpers.TestSpec
import components.Dsl._
import io.IOHandler
import org.mockito.Mockito._

import scala.util.Random

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

    it("should save model") {
      val objective = max(-1("a") + 2("b") - 1.5("c"))
      val constraints = Set(
          1("a") + 3("b") <= 5,
          3("a") - 1("b") == 0,
          1("c") <= 10
        )
      val ioHandler = mock(classOf[IOHandler])
      val fileName = Random.nextString(10)
      val expectedBody =
        s"""Maximize
           |  -a + 2b - 1.5c
           |Subject To
           |  c0: a + 3b <= 5
           |  c1: 3a - b = 10
           |  c2: c <= 10
           |Bounds
           |  0 <= a <= 1
           |  0 <= b <= 1
           |  0 <= c <= 1
           |Generals
           |  a b c
           |End""".stripMargin

      Model(constraints, objective).save(fileName, ioHandler)
      verify(ioHandler).save(fileName, expectedBody)
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
