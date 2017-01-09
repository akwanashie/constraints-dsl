package components

import helpers.TestSpec

import scala.util.Random

class VariableSpec extends TestSpec {
  describe("Variable") {
    it("should not accept name empty string") {
      val expectedError = the [IllegalArgumentException] thrownBy Variable("")
      expectedError.getMessage should endWith("Variable name should not be empty.")
    }

    it("should accept only alphabets for the beginning of names") {
      val expectedError = the [IllegalArgumentException] thrownBy Variable("1")
      expectedError.getMessage should endWith("Variable name should start with a character.")
    }

    it("should have a default domain range") {
      val name = Random.nextString(10)
      Variable(name).min shouldEqual 0.0
      Variable(name).max shouldEqual 1.0
    }

    it("should have a default value") {
      val name = Random.nextString(10)
      Variable(name).value shouldEqual 0.0
    }

    it("name method should return the name of the variable") {
      val name = Random.nextString(10)
      Variable(name).name shouldEqual name
    }
  }
}
