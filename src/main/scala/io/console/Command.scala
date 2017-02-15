package io.console

trait Command {
  val stringRep: String
  val execute: () => Unit
}

object Command {
  def apply(stringRep: String): Command = stringRep match {
    case Exit.stringRep => Exit
    case Help.stringRep => Help
    case EmptyCommand.stringRep => EmptyCommand
    case _ => throw new CommandException(s"Unrecognised command: $stringRep. Type 'help' to view options")
  }
}

class CommandException(cause: String) extends Exception(cause)