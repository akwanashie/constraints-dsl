package io.console.commands

import io.console.ConsoleState

object Solve extends Command {
  override val stringRep: String = "solve"

  // TODO Implement this...
  override val execute = (state: ConsoleState) => throw new CommandException("Not implemented yet...")

  override val description: String = "Solves the model"
}
