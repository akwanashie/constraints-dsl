package components

sealed trait Direction {
  override def toString: String
}

object MAX extends Direction {
  override def toString = "max"
}
object MIN extends Direction{
  override def toString = "min"
}