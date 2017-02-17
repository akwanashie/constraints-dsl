package io.console

import io.console.commands.{Command, Exit}
import org.jline.reader.{LineReaderBuilder, UserInterruptException}
import org.jline.terminal.{Terminal, TerminalBuilder}

import scala.util.Try

object ConstraintsTerminal extends App {
  private val terminal: Terminal = TerminalBuilder.builder().name("Constraints Solver").build()
  private val lineReader = LineReaderBuilder.builder().terminal(terminal).build()

  readLine("constraints-solver> ")

  private def readLine(prompt: String): Try[_] = {
    Try(lineReader.readLine(prompt))
      .map(Command.apply)
      .map(_.execute())
      .recover(processErrors)
      .map(_ => readLine(prompt))
  }

  private def processErrors: PartialFunction[Throwable, Unit] = {
    case error: UserInterruptException => Exit.execute()
    case error: Throwable => println(s"Error: ${error.getMessage}")
  }
}
