package components

import components.SingleExpression._

abstract sealed case class Objective(terms: Set[Term], direction: Direction)

object Objective {
  def apply(terms: Set[Term], direction: Direction): Objective = {
    validate(terms)
    new Objective(terms, direction) {}
  }
}