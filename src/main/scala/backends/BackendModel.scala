package backends

import components.{Model, Solution}

import scala.util.Try

trait BackendModel {
  val baseModel: Model

  def solve: Try[Solution]
}
