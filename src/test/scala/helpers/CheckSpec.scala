package helpers

import org.scalacheck.Gen
import org.scalacheck.Prop._
import org.scalatest.prop.Checkers

trait CheckSpec extends TestSpec with Generators with Checkers {
  def testAll[T, P](generator: Gen[T])(property: T => Boolean) = check(forAll(generator)(property))
}
