package io.console.commands

import components.Dsl._
import components._
import io.console.ConsoleState

abstract class Optimise extends Command {
  override val execute = (state: ConsoleState) => {
    val components = state.commandString.split("[ ]+").tail.mkString.replaceAll(" ", "")
    val termRegex = "[+-]*[\\d]*[.]*[\\d]*[A-Za-z]+".r
    val terms: Set[Term] = termRegex.findAllIn(components)
      .toSet[String]
      .map(x => stringToTermConverter(x))

    val direction = if(stringRep == MAX.toString) MAX else MIN
    val objective = Objective(terms, direction)
    val model = state.model
      .map(_.withObjective(objective))
      .getOrElse(throw new Exception("No constraints have been defined yet."))

    state.copy(model = Some(model))
  }
}

object Max extends Optimise {
  override val stringRep: String = "max"

  override val description: String = "Adds an maximising optimisation function"
}

object Min extends Optimise {
  override val stringRep: String = "min"

  override val description: String = "Adds an minimising optimisation function"
}