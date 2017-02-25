package io.console.commands

import io.console.ConsoleState

object Print extends Command {
  override val stringRep: String = "print"
  override val execute = (state: ConsoleState) => {
    println(state.model.map(_.toLpString).getOrElse("No model."))
    state
  }
}
