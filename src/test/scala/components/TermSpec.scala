package components

import components.Dsl._
import helpers.TestSpec

class TermSpec extends TestSpec {
  describe("DSL: Term") {
    it("number and string to term") {
      val term: Term = 3("a")
      term shouldEqual Term(3, Variable("a"))
    }

    it("negative prefix") {
      val term: Term = -45("a")
      term shouldEqual Term(-45, Variable("a"))
    }
  }
}
