package io.console.commands

import io.console.ConsoleState

object Print extends Command {
  override val stringRep: String = "print"

  override val execute = (state: ConsoleState) => {
    println(
      s"""
         |${state.model.map(_.toLpString).getOrElse("No model.")}
         |
         |${state.solution.map(_.toString).getOrElse("No solution.")}
       """.stripMargin
    )
    state
  }

  override val description: String = "Prints the model to console"
}
