package components


abstract sealed case class Constraint(lhsTerms: Set[Term], equality: Equality, rhsValue: Double)

object Constraint {
  def apply(lhsTerms: Set[Term], equality: Equality, rhsValue: Double): Constraint = {
    require(lhsTerms.nonEmpty, "Constraints must contain at least one term.")
    require(lhsTerms.map(_.variable).size == lhsTerms.size, s"Some variable appear multiple times.")

    new Constraint(lhsTerms, equality, rhsValue) {}
  }
}
