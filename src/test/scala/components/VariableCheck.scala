package components

import helpers.CheckSpec

import scala.util.Try

class VariableCheck extends CheckSpec {
  describe("Variable") {
    it("should not accept variable names that do not start with a letter") {
      test(invalidVariableNames) {
        (variableName: String) => Try(Variable(variableName)).isFailure
      }
    }

    it("should accept only alphabets for the beginning of names") {
      test(validVariableNames) {
        (variableName: String) => Try(Variable(variableName)).isSuccess
      }
    }
  }
}
