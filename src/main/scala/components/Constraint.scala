package components
import components.SingleExpression._

abstract sealed case class Constraint(lhsTerms: Set[Term], equality: Equality, rhsValue: Double)

object Constraint {
  def apply(terms: Set[Term], equality: Equality, rhsValue: Double): Constraint = {
    validate(terms)
    new Constraint(terms, equality, rhsValue) {}
  }
}
