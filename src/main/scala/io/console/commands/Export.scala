package io.console.commands

import io.console.ConsoleState

object Export extends Command {
  override val stringRep: String = "export"
  override val execute = (state: ConsoleState) => {
    state.commandString.split("[ ]+").toList match {
      case command :: path :: Nil =>
        state.model.map(_.save(path))
        state
      case _ => throw new CommandException("Please provide a file name/path")
    }
  }
}
