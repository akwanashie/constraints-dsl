package io.console.commands

import io.console.ConsoleState

object Help extends Command {
  override val stringRep: String = "help"
  override val execute = (state: ConsoleState) => {
    println("Available commands:")
    state
  }
}