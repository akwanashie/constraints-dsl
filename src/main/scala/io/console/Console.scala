package io.console

import io.console.commands.{Blank, Command, Exit}
import org.jline.reader.{LineReaderBuilder, UserInterruptException}
import org.jline.terminal.{Terminal, TerminalBuilder}

import scala.util.Try

object Console extends App {
  private val terminal: Terminal = TerminalBuilder.builder().name("Constraints Solver").build()
  private val lineReader = LineReaderBuilder.builder().terminal(terminal).build()

  readLine("constraints-solver> ", BlankState)

  private def readLine(prompt: String, state: ConsoleState): Try[_] = {
    Try(lineReader.readLine(prompt))
      .map(inputString => state.update(inputString))
      .map(currentState => currentState.executeCommand)
      .recover(processErrors(state))
      .map(currentState => readLine(prompt, currentState))
  }

  private def processErrors: ConsoleState => PartialFunction[Throwable, ConsoleState] = (state: ConsoleState) => {
    case error: UserInterruptException => Exit.execute(state)
    case error: Throwable => {
      println(s"Error: ${error.getMessage}")
      state
    }
  }
}
