package components

case class Variable(name: String = "",
                    min: Double = Variable.DefaultMin,
                    max: Double = Variable.DefaultMax,
                    value: Double = Variable.DefaultValue) {
  validate()

  private def validate() = {
    require(name.nonEmpty, "Variable name should not be empty.")
    require(name.charAt(0).isLetter, "Variable name should start with a character.")
  }
}

object Variable {
  val DefaultMin = 0.0
  val DefaultMax = 1.0
  val DefaultValue = 0.0
}