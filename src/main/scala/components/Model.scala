package components

import io.{FileIOHandler, IOHandler}

import scala.util.Try

abstract sealed case class Model(constraints: Set[Constraint], objective: Objective) {
  def +(constraint: Constraint): Model = Model(constraints + constraint, objective)

  def withObjective(newObjective: Objective): Model = Model(constraints, newObjective)

  def variables: Set[Variable] = constraints.flatMap(_.lhsTerms.map(_.variable))

  def save(fileName: String, iOHandler: IOHandler = new FileIOHandler): Try[Unit] = {
    val direction = objective.direction match {
      case MAX => "Maximize"
      case MIN => "Minimize"
    }
    val applySpaces = (line: String) => {
      val firstChar = line.substring(0, 1) match {
        case "+" => ""
        case "-" => "-"
        case x => x
      }
      firstChar + line.substring(1).replaceAll("\\+", "+ ").replaceAll("\\-", "- ")
    }
    val termsToString = (terms: Set[Term]) => applySpaces(terms.map(_.toString).mkString(" "))

    val objectiveConstraints = termsToString(objective.terms)

    val constraintsLines = constraints.zipWithIndex.map { case (constraint, index) =>
      val lhs = termsToString(constraint.lhsTerms)
      val value = constraint.rhsValue.isValidInt match {
        case true => s"${constraint.rhsValue.toInt}"
        case false => s"${constraint.rhsValue}"
      }
      s"c$index: $lhs ${constraint.equality} $value"
    } mkString "\r\n  "

    val removeDecimals = (number: Double) => if (number.isValidInt) s"${number.toInt}" else s"$number"
    val bounds = variables
      .map(variable => s"${removeDecimals(variable.min)} <= ${variable.name} <= ${removeDecimals(variable.max)}")
      .mkString("\r\n  ")

    val body =
      s"""$direction
         |  $objectiveConstraints
         |Subject To
         |  $constraintsLines
         |Bounds
         |  $bounds
         |Generals
         |  ${variables.map(_.name).mkString(" ")}
         |End""".stripMargin

    iOHandler.save(fileName, body)
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