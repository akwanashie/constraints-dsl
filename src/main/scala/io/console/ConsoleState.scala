package io.console

import components.Model
import io.console.commands.{Blank, Command}

// TODO Convert Option[Model] to a NullModel pattern
case class ConsoleState(commandString: String, command: Command, model: Option[Model]) {
  def executeCommand: ConsoleState = command.execute(this.copy())

  def update(commandString: String): ConsoleState =
    this.copy(commandString = commandString, command = Command(commandString))
}

object BlankState extends ConsoleState("", Blank, None)