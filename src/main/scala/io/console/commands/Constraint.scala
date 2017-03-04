package io.console.commands

import components.Dsl._
import components._
import components.{Constraint => ModelConstraint}
import io.console.ConsoleState

import scala.util.Try

object Constraint extends Command {
  override val stringRep: String = ""

  override val description: String = "Adds a constraint to the model"

  override val execute = (state: ConsoleState) =>
    Try {
      val regex = "([A-Za-z0-9 +-]+)([><=]{2})([ \\d]+[.]*[\\d]*)".r
      val regex(termsString, equalityString, rhsString) = state.commandString

      val termRegex = "[+-]*[\\d]*[.]*[\\d]*[A-Za-z]+".r
      termRegex.findAllIn(termsString.replaceAll(" ", ""))
      val terms: Set[Term] = termRegex.findAllIn(termsString.replaceAll(" ", ""))
        .toSet[String]
        .map(x => stringToTermConverter(x))

      val constraint = ModelConstraint(terms, equalityString, rhsString.trim.toDouble)
      val newModel = state.model match {
        case None => Model(Set(constraint), None)
        case Some(model) => model.+(constraint)
      }

      state.copy(model = Some(newModel))
    } getOrElse(throw new Exception(s"Could not parse constraint '${state.commandString}'"))
}