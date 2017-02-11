package components

case class Term(prefix: Double, variable: Variable) {
  def unary_- = copy(prefix = -prefix)

  override def toString: String = {
    val sign = prefix > 0 match {
      case true => "+"
      case false => ""
    }

    (prefix.isValidInt, prefix) match {
      case (_, 1.0) => s"+${variable.name}"
      case (_, -1.0) => s"-${variable.name}"
      case (true, _) => s"$sign${prefix.toInt}${variable.name}"
      case (false, _) => s"$sign$prefix${variable.name}"
    }
  }
}