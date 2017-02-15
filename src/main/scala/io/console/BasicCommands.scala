package io.console

object Exit extends Command {
  override val stringRep: String = "exit"
  override val execute = () => {
    println("bye!")
    System.exit(0)
  }
}

object Help extends Command {
  override val stringRep: String = "help"
  override val execute = () => println("Available commands:")
}

object EmptyCommand extends Command {
  override val stringRep: String = ""
  override val execute = () => ()
}