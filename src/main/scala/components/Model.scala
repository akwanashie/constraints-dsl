package components

import io.{FileIOHandler, IOHandler}

import scala.util.Try

abstract sealed case class Model(constraints: Set[Constraint], objective: Objective) {
  def +(constraint: Constraint): Model = Model(constraints + constraint, objective)

  def withObjective(newObjective: Objective): Model = Model(constraints, newObjective)

  def variables: Set[Variable] = constraints.flatMap(_.lhsTerms.map(_.variable))

  def save(fileName: String, iOHandler: IOHandler = new FileIOHandler): Try[Unit] = {
    val body =
      s"""${objective.direction}: ${termsToString(objective.terms)};
         |$constraintsToString
         |$bounds
         |int ${variables.map(_.name).mkString(" ")};""".stripMargin

    iOHandler.save(fileName, body)
  }

  private def termsToString(terms: Set[Term]) = {
    val applySpaces = (line: String) => {
      val firstChar = line.substring(0, 1) match {
        case "+" => ""
        case "-" => "-"
        case x => x
      }
      firstChar + line.substring(1).replaceAll("\\+", "+ ").replaceAll("\\-", "- ")
    }

    applySpaces(terms.map(_.toString).mkString(" "))
  }

  private def constraintsToString(): String = {
    constraints.map { constraint =>
      val lhs = termsToString(constraint.lhsTerms)
      val value = constraint.rhsValue.isValidInt match {
        case true => s"${constraint.rhsValue.toInt}"
        case false => s"${constraint.rhsValue}"
      }
      s"$lhs ${constraint.equality} $value;"
    } mkString "\r\n"
  }

  private def bounds: String = {
    val removeDecimals = (number: Double) => if (number.isValidInt) s"${number.toInt}" else s"$number"
    variables
      .map(variable => s"""
           |${variable.name} >= ${removeDecimals(variable.min)};
           |${variable.name} <= ${removeDecimals(variable.max)};
         """.stripMargin.trim)
      .mkString("\r\n").trim
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