package helpers

import org.scalacheck.Gen
import org.scalacheck.Arbitrary._

trait Generators {
  implicit lazy val invalidVariableNames: Gen[String] = arbitrary[String]
    .filter(value => value.nonEmpty && !value.charAt(0).isLetter)
  implicit lazy val validVariableNames: Gen[String] = arbitrary[String]
    .filter(value => value.nonEmpty && value.charAt(0).isLetter)
}
