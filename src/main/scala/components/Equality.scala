package components

sealed trait Equality

object EQ extends Equality {
  override def toString: String = "="
}

object LEQ extends Equality {
  override def toString: String = "<="
}

object GEQ extends Equality {
  override def toString: String = ">="
}