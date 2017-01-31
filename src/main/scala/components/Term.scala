package components

case class Term(prefix: Double, variable: Variable) {
  def unary_- = copy(prefix = -prefix)
}