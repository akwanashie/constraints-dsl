package io.console.commands

object Help extends Command {
  override val stringRep: String = "help"
  override val execute = (command: String) => println("Available commands:")
}