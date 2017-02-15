package io.console

import org.jline.reader.LineReaderBuilder
import org.jline.terminal.{Terminal, TerminalBuilder}

import scala.util.Try

object ConstraintsTerminal extends App {
  val terminal: Terminal = TerminalBuilder.builder().name("Constraints Solver").build()
  val lineReader = LineReaderBuilder.builder().terminal(terminal).build()

  readLine("constraints-solver> ")

  def readLine(prompt: String): Try[_] = {
    Try(lineReader.readLine(prompt))
      .map(Command.apply)
      .map(_.execute())
      .recover({ case error => println(s"Error: ${error.getMessage}") })
      .map(_ => readLine(prompt))
  }
}
