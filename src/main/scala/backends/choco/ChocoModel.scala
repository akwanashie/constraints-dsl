package backends.choco

import backends.{BackendModel, BackendSolveException}
import components.{Model, Solution, Variable}
import org.chocosolver.solver.variables.IntVar
import org.chocosolver.solver.{Model => ChocoSolverModel}

import scala.util.Try


case class ChocoModel(baseModel: Model) extends BackendModel {

  private lazy val chocoModel = new ChocoSolverModel()

  // TODO are we restricting variables to Int for now? if so, then maybe do the same for components.Model
  private lazy val chocoVariables = generateChocoVariables(chocoModel)

  private lazy val chocoModelWithConstraints = applyConstraints(chocoModel, chocoVariables)

  private lazy val chocoModelWithConstraintsAndObjective = baseModel.objective
    .map(_ => setObjective(chocoModelWithConstraints, chocoVariables))
    .getOrElse(chocoModelWithConstraints)

  private def generateChocoVariables(chocoModel: ChocoSolverModel): Map[Variable, IntVar] = {
    baseModel.variables
      .map(baseVariable => (baseVariable, chocoModel.intVar(baseVariable.name, baseVariable.min.toInt, baseVariable.max.toInt)))
      .toMap
  }

  private def applyConstraints(chocoModel: ChocoSolverModel, chocoVariables: Map[Variable, IntVar]): ChocoSolverModel = {
    baseModel.constraints.foldLeft(chocoModel) { case (currentModel, constraint) =>
      // TODO what if a constraint does not have all the variables in it? This will fail!!!
      val varMap: Set[(IntVar, Int)] = constraint.lhsTerms.map(term =>
        (chocoVariables(term.variable), term.prefix.toInt)
      )

      currentModel.scalar(
        varMap.toSeq.map(_._1).toArray,
        varMap.toSeq.map(_._2).toArray,
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
      varMap.toSeq.map(_._1).toArray,
      varMap.toSeq.map(_._2).toArray,
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

      val objectiveValue: Option[Double] = baseModel.objective.map(objective => objective.terms.toSeq
        .map(term => term.prefix * chocoVariables(term.variable).getValue)
        .sum)

      Solution(solvedVariables, objectiveValue)
    }
  }
}
