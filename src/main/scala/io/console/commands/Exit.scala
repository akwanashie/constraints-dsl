package io.console.commands

import io.console.ConsoleState

object Exit extends Command {
  override val stringRep: String = "exit"

  override val execute = (state: ConsoleState) => {
    println("bye!")
    System.exit(0)
    state
  }
}
