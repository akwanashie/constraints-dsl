package io.console.commands

import io.console.ConsoleState

object Help extends Command {
  override val stringRep: String = "help"

  override val execute = (state: ConsoleState) => {
    println(
      s"""
          |Available commands:
          |************************
          |[expression] ${margin(3)} ${Constraint.description}
          |min [expression] ${margin(2)} ${Min.description}
          |max [expression] ${margin(2)} ${Max.description}
          |$generateHelpDescriptions
       """.stripMargin)
    state
  }

  override val description: String = "Prints instructions"

  private def margin(width: Int): String = "\t" * width

  private def generateHelpDescriptions: String = {
    Seq(Clear, Print, Save, Exit, Load, Export) map { command =>
      s"${command.stringRep} ${margin(4)} ${command.description}"
    } mkString "\r\n"
  }
}