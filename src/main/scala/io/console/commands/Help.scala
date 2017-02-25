package io.console.commands

import io.console.ConsoleState

object Help extends Command {
  override val stringRep: String = "help"
  override val execute = (state: ConsoleState) => {
    println(
      s"""Available commands:
         |[expression]            adds a constraint to the model
         |min/max [expression]    sets the objective function
         |clear                   clears the model
         |help                    displays available comments
         |print                   prints the current model
         |save                    saves the model to file
         |exit                    exits the console
       """.stripMargin)
    state
  }
}