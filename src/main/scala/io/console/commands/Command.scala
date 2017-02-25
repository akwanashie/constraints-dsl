package io.console.commands

import io.console.ConsoleState

trait Command {
  val stringRep: String

  val startsWith = (commandString: String) => commandString.startsWith(stringRep)

  val execute: ConsoleState => ConsoleState
}

object Command {
  private val supportedCommands: Seq[Command] = Seq(
    Min,
    Max,
    Print,
    Blank,
    Exit,
    Help,
    Constraint
  )

  def apply(stringRep: String, supportedCommands: Seq[Command] = supportedCommands): Command = {
    supportedCommands
      .find(_.startsWith(if (stringRep.trim.isEmpty) Blank.stringRep else stringRep))
      .getOrElse(throw new CommandException(s"Unrecognised command: '$stringRep'. Type 'help' to view options"))
  }
}

