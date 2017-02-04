package components

sealed class Direction (stringRep: String) {
  override def toString: String = stringRep
}

object MAX extends Direction("Max")
object MIN extends Direction("Min")