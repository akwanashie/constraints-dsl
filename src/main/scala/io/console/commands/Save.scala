package io.console.commands

import io.console.ConsoleState

object Save extends Command {
  override val stringRep: String = "save"

  // TODO Implement this...
  override val execute = (state: ConsoleState) => throw new CommandException("Not implemented yet...")

  override val description: String = "Saves the model to file"
}
