package io.console.commands

import io.console.ConsoleState

object Save extends Command {
  override val stringRep: String = "save"
  override val execute = (state: ConsoleState) => {
    val path = state.commandString.split("[ ]+").toList(1)
    state.model.map(_.save(path))
    state
  }
}
