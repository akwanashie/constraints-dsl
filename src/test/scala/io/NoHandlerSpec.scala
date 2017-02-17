package io

import helpers.TestSpec

import scala.util.Random

class NoHandlerSpec extends TestSpec {
  describe("NoHandler") {
    it("should always return a success") {
      NoHandler.save(Random.nextString(10), Random.nextString(10)).isSuccess shouldBe true
    }

    it("should not return any value") {
      NoHandler.save(Random.nextString(10), Random.nextString(10)).get shouldBe ()
    }
  }
}
