package io.console.commands

import backends.choco.ChocoModel
import components.Model
import io.console.ConsoleState

import scala.util.{Failure, Success}

object Solve extends Command {
  override val stringRep: String = "solve"

  override val execute = (state: ConsoleState) => {
    val model: Model = state.model.getOrElse(throw new CommandException("No model to solve."))
    val backendModel = ChocoModel(model)

    backendModel.solve match {
      case Success(solution) =>
        println(s"Solution: ${solution.toString}")
        state.copy(solution = Some(solution))
      case Failure(error) =>
        throw error
    }
  }

  override val description: String = "Solves the model"
}
