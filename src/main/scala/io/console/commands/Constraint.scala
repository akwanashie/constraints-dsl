package io.console.commands

import components.Dsl._
import components._
import components.{Constraint => ModelConstraint}
import io.console.ConsoleState

import scala.util.Try

object Constraint extends Command {
  override val stringRep: String = ""

  override val execute = (state: ConsoleState) => Try {
    val regex = "([A-Za-z0-9 +-]+)([><=]{2})([ \\d]+[.]*[\\d]*)".r
    val regex(termsString, equalityString, rhsString) = state.commandString

    val terms: Set[Term] = termsString.trim.split("[ ]+").toSet[String].map(x => stringToTermConverter(x))
    val constraints = ModelConstraint(terms, equalityString, rhsString.trim.toDouble)

    state.copy(model = Some(Model(Set(constraints), None)))
  } getOrElse(throw new Exception(s"Could not parse constraint '${state.commandString}'"))
}