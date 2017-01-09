package helpers

import org.scalatest.{FunSpec, Matchers}

import scala.util.Random

trait TestSpec extends FunSpec with Matchers {
  def randomVariableName = s"x_${Random.nextString(10)}"
}
