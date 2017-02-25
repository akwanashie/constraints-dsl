package io.console.commands

import components.Dsl._
import components._
import io.console.ConsoleState

import scala.util.Try

object Constraint extends Command {
  override val stringRep: String = ""

  override val execute = (state: ConsoleState) => {
    println("Adding constraint...")
    state
  }

  private def toTerm: String => Term = (termString: String) => Try {
    val regex = "([\\d]+[.]*[\\d]*)([A-Za-z]+)".r
    val regex(prefix, variableName) = termString
    Term(prefix.toDouble, variableName)
  }.getOrElse(throw new Exception(s"Could not parse input '$termString'"))
}