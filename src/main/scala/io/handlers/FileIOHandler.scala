package io.handlers

import java.io.{File => JFile}

import better.files._

import scala.util.Try

class FileIOHandler extends IOHandler {
  override def save(fileName: String, body: String): Try[Unit] = Try {
    File(fileName).overwrite(body)
  }
}
