package components

abstract sealed case class Model(constraints: Set[Constraint], objective: Objective) {
  def +(constraint: Constraint): Model = Model(constraints + constraint, objective)

  def withObjective(newObjective: Objective): Model = Model(constraints, newObjective)
}

object Model {
  def apply(constraints: Set[Constraint], objective: Objective): Model = {
    require(constraints.nonEmpty, "At least one constraint is required.")
    require(!unusedVariablesExist(constraints, objective), "Unused variable in objective function.")
    new Model(constraints, objective) {}
  }

  private def unusedVariablesExist(constraints: Set[Constraint], objective: Objective): Boolean = {
    objective.terms.map(_.variable).diff(constraints.flatMap(_.lhsTerms.map(_.variable))).nonEmpty
  }
}