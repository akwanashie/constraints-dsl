package io.handlers

import scala.util.Try

trait IOHandler {
  def save(fileName: String, body: String): Try[Unit]
}


