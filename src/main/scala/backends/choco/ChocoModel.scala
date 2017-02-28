package backends.choco

import backends.{BackendModel, BackendSolveException}
import components.{Model, Solution, Variable}
import org.chocosolver.solver.variables.IntVar
import org.chocosolver.solver.{Model => ChocoSolverModel}

import scala.util.Try


case class ChocoModel(baseModel: Model) extends BackendModel {

  val chocoModel = new ChocoSolverModel()

  // TODO are we restricting variables to Int for now? if so, then maybe do the same for components.Model
  val chocoVariables = generateChocoVariables(chocoModel)

  val chocoModelWithConstraints = applyConstraints(chocoModel, chocoVariables)

  val chocoModelWithConstraintsAndObjective = baseModel.objective
    .map(_ => setObjective(chocoModelWithConstraints, chocoVariables))
    .getOrElse(chocoModelWithConstraints)

  private def generateChocoVariables(chocoModel: ChocoSolverModel): Map[Variable, IntVar] = {
    baseModel.variables
      .map(baseVariable => (baseVariable, chocoModel.intVar(baseVariable.name, baseVariable.min.toInt, baseVariable.max.toInt)))
      .toMap
  }

  private def applyConstraints(chocoModel: ChocoSolverModel, chocoVariables: Map[Variable, IntVar]): ChocoSolverModel = {
    baseModel.constraints.foldLeft(chocoModel) { case (currentModel, constraint) =>
      val varMap: Set[(IntVar, Int)] = constraint.lhsTerms.map(term =>
        (chocoVariables(term.variable), term.prefix.toInt)
      )

      currentModel.scalar(
        varMap.map(_._1).toArray,
        varMap.map(_._2).toArray,
        constraint.equality.toString,
        constraint.rhsValue.toInt
      ).post()

      currentModel
    }
  }

  private def setObjective(chocoModel: ChocoSolverModel, chocoVariables: Map[Variable, IntVar]): ChocoSolverModel = {
    val varMap: Set[(IntVar, Int)] = baseModel.objective.get.terms.map(term =>
      (chocoVariables(term.variable), term.prefix.toInt)
    )

    val objVar = chocoModel.intVar("opt", IntVar.MIN_INT_BOUND, IntVar.MAX_INT_BOUND)

    chocoModel.scalar(
      varMap.map(_._1).toArray,
      varMap.map(_._2).toArray,
      "=",
      objVar
    ).post()

    chocoModel.setObjective(true, objVar)
    chocoModel
  }

  override def solve: Try[Solution] = {
    Try {
      if (!chocoModelWithConstraintsAndObjective.getSolver.solve()) {
        throw new BackendSolveException(s"Failed so solve model.")
      }

      val solvedVariables: Set[Variable] = chocoVariables map { case (baseVar, chocoVar) =>
        baseVar.copy(value = chocoVar.getValue)
      } toSet

      val objectiveValue: Option[Double] = baseModel.objective.map(objective => objective.terms
        .map(term => term.prefix * chocoVariables(term.variable).getValue)
        .sum)

      Solution(solvedVariables, objectiveValue)
    }
  }
}
