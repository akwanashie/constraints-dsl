package io.console.commands

object Exit extends Command {
  override val stringRep: String = "exit"
  override val execute = () => {
    println("bye!")
    System.exit(0)
  }
}
