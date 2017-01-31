package components

sealed class Equality(stringRep: String) {
  override def toString: String = stringRep
}

object EQ extends Equality("=")
object LEQ extends Equality("<=")
object GEQ extends Equality(">=")