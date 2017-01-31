package components

import helpers.{CheckSpec, TestSpec}
import components.Dsl._

import scala.util.Try

class VariableSpec extends TestSpec with CheckSpec {
  describe("A variable") {
    it("should not accept name empty string") {
      val expectedError = the [IllegalArgumentException] thrownBy Variable("")
      expectedError.getMessage should endWith("Variable name should not be empty.")
    }

    it("should accept only alphabets for the beginning of names") {
      val expectedError = the [IllegalArgumentException] thrownBy Variable("1")
      expectedError.getMessage should endWith("Variable name should start with a character.")
    }

    it("should have a default domain range") {
      Variable(randomVariableName).min shouldEqual 0.0
      Variable(randomVariableName).max shouldEqual 1.0
    }

    it("should have a default value") {
      Variable(randomVariableName).value shouldEqual 0.0
    }

    it("name method should return the name of the variable") {
      val name = randomVariableName
      Variable(name).name shouldEqual name
    }
  }

  describe("ScalaCheck: A variable") {
    it("should not accept variable names that do not start with a letter") {
      testAll(invalidVariableNames) {
        (variableName: String) => Try(Variable(variableName)).isFailure
      }
    }

    it("should accept only alphabets for the beginning of names") {
      testAll(validVariableNames) {
        (variableName: String) => Try(Variable(variableName)).isSuccess
      }
    }
  }

  describe("DSL: Variable") {
    it("string to variable") {
      val a: Variable = "a"
      Variable("a") shouldEqual a
    }
  }
}
