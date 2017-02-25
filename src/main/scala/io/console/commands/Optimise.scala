package io.console.commands

import components.Dsl._
import components._
import io.console.ConsoleState

import scala.util.Try

abstract class Optimise extends Command {
  override val execute = (state: ConsoleState) => {
    val components = state.commandString.split("[ ]+").toSet.tail
    val terms = components.map(toTerm)
    val direction = if(stringRep == MAX.toString) MAX else MIN
    val objective = Objective(terms, direction)
    val model = state.model
      .map(_.withObjective(objective))
      .getOrElse(throw new Exception("No constraints have been defined yet."))

    state.copy(model = Some(model))
  }

  private def toTerm: String => Term = (termString: String) => Try {
    val regex = "([\\d]+[.]*[\\d]*)([A-Za-z]+)".r
    val regex(prefix, variableName) = termString
    Term(prefix.toDouble, variableName)
  }.getOrElse(throw new Exception(s"Could not parse input '$termString'"))
}

object Max extends Optimise {
  override val stringRep: String = "max"
}

object Min extends Optimise {
  override val stringRep: String = "min"
}