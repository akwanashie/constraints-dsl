package io

import scala.util.{Success, Try}

object NoHandler extends IOHandler {
  override def save(fileName: String, body: String): Try[Unit] = Success()
}
