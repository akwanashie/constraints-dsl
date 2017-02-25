package io.console.commands

import io.console.ConsoleState

object Blank extends Command {
  override val stringRep: String = "blank"
  override val execute = (state: ConsoleState) => state
}
