package components

import io.{FileIOHandler, IOHandler}
import io.formats.{Format, LP, LPFormat}

import scala.util.Try

abstract sealed case class Model(constraints: Set[Constraint], objective: Objective) extends LPFormat {

  def +(constraint: Constraint): Model = Model(constraints + constraint, objective)

  def withObjective(newObjective: Objective): Model = Model(constraints, newObjective)

  def save(fileName: String, iOHandler: IOHandler = new FileIOHandler, format: Format = LP): Try[Unit] = {
    val lpString = format match {
      case LP => toLpString
    }

    iOHandler.save(fileName, lpString)
  }
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