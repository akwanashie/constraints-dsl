package io.console.commands

import io.console.ConsoleState

object Load extends Command {
  override val stringRep: String = "load"

  override val description: String = "Loads a model from file"

  // TODO Implement this...
  override val execute = (state: ConsoleState) => throw new CommandException("Not implemented yet...")
}
