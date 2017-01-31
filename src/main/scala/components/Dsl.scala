package components

object Dsl {
  implicit def stringToVariable(name: String): Variable = Variable(name)

  implicit class IntegerToTermConverter(prefix: Int) extends ((String) => Term) {
    def apply(name: String): Term = Term(prefix, Variable(name))
  }
}
