package io.console.commands

trait Command {
  val stringRep: String
  val execute: () => Unit
}

object Command {
  private val supportedCommands: Seq[Command] = Seq(
    Blank,
    Exit,
    Help
  )

  def apply(stringRep: String, supportedCommands: Seq[Command] = supportedCommands): Command = supportedCommands
    .find(_.stringRep == stringRep)
    .getOrElse(throw new CommandException(s"Unrecognised command: $stringRep. Type 'help' to view options"))
}

