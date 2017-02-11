package components

sealed trait Direction

object MAX extends Direction {
  override def toString = "max"
}
object MIN extends Direction{
  override def toString = "min"
}