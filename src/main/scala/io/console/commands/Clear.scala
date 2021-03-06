package io.console.commands

import io.console.ConsoleState

object Clear extends Command {
  override val stringRep: String = "clear"
  override val execute = (state: ConsoleState) => state.copy(model = None, solution = None)
  override val description: String = "Clears the model from memory"
}
