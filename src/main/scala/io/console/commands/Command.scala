package io.console.commands

trait Command {
  val stringRep: String

  val startsWith = (commandString: String) => commandString.startsWith(stringRep)

  val execute: String => Unit
}

object Command {
  private val supportedCommands: Seq[Command] = Seq(
    Blank,
    Exit,
    Help
  )

  def apply(stringRep: String, supportedCommands: Seq[Command] = supportedCommands): Command = {
    supportedCommands
      .find(command => {
        val modifiedString = if (stringRep.trim.isEmpty) Blank.stringRep else stringRep
        command.startsWith(modifiedString)
      })
      .getOrElse(throw new CommandException(s"Unrecognised command: '$stringRep'. Type 'help' to view options"))
  }
}

