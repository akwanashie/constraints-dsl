package components

object SingleExpression {
  def validate(terms: Set[Term]): Unit = {
    require(terms.nonEmpty, "At least one term is required.")
    require(terms.map(_.variable).size == terms.size, s"Some variable appear multiple times.")
  }
}
