package io.console.commands

object Blank extends Command {
  override val stringRep: String = "blank"
  override val execute = (command: String) => ()
}
