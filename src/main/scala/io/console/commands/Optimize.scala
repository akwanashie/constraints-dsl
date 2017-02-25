package io.console.commands

import components.Dsl._
import components.{MAX, Objective, Term}
import io.console.ConsoleState

import scala.util.Try

object Optimize extends Command {
  override val stringRep: String = "max"

  override val execute = (state: ConsoleState) => {
    val components = state.commandString.split(" ").toSet.tail
    val terms = components.map(toTerm)
    val objective = Objective(terms, MAX)
    state
  }

  private def toTerm: String => Term = (termString: String) => Try {
    val regex = "([\\d]+[.]*[\\d]*)([A-Za-z]+)".r
    val regex(prefix, variableName) = termString
    Term(prefix.toDouble, variableName)
  }.getOrElse(throw new Exception(s"Could not parse input '${termString}'"))
}