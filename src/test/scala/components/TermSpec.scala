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

    it("two terms combined with a '+' sign") {
      val term1: Term = 1("a")
      val term2: Term = 1("b")

      val terms: Set[Term] = 1("a") + 1("b")
      terms shouldEqual Set(term1, term2)
    }

    it("two terms combined with a '-' sign: case 1") {
      val term1: Term = 1("a")
      val term2: Term = -1("b")

      val terms: Set[Term] = 1("a") - 1("b")
      terms shouldEqual Set(term1, term2)
    }

    it("two terms combined with a '-' sign: case 2") {
      val term1: Term = -1("a")
      val term2: Term = -1("b")

      val terms: Set[Term] = -1("a") - 1("b")
      terms shouldEqual Set(term1, term2)
    }

    it("two terms combined with a '-' sign: case 3") {
      val term1: Term = -1("a")
      val term2: Term = 1("b")

      val terms: Set[Term] = -1("a") - -1("b")
      terms shouldEqual Set(term1, term2)
    }
  }
}
