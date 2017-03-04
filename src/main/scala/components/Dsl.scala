package components

object Dsl {
  implicit def stringToVariable(name: String): Variable = Variable(name)

  implicit class IntegerToTermConverter(prefix: Int) extends ((String) => Term) {
    def apply(name: String): Term = Term(prefix, Variable(name))
  }

  implicit class DoubleToTermConverter(prefix: Double) extends ((String) => Term) {
    def apply(name: String): Term = Term(prefix, Variable(name))
  }

  implicit def stringToTermConverter(termString: String): Term = {
      val regex = "([+\\- ]*[\\d]*[.]*[\\d]*)([A-Za-z]+)".r
      val regex(prefix, variableName) = termString
      val modifiedPrefixString = if(Seq("", "+", "-").contains(prefix.trim)) s"${prefix.trim}1.0" else prefix.trim
      Term(modifiedPrefixString.toDouble, variableName)
  }

  implicit def stringToEquality(equalityString: String): Equality = {
    equalityString match {
      case "=" => EQ
      case "==" => EQ
      case "<=" => LEQ
      case ">=" => GEQ
    }
  }

  implicit def convertTermToTermSet(term: Term): TermSet = TermSet(Set(term))

  implicit def convertTermSetToSetOfTerms(termSet: TermSet): Set[Term] = termSet.terms

  implicit class TermSet(val terms: Set[Term]) {
    def +(newTerm: Term): TermSet = terms + newTerm

    def -(newTerm: Term): TermSet = terms + -newTerm

    def ==(value: Double): Constraint = Constraint(terms, EQ, value)

    def >=(value: Double): Constraint = Constraint(terms, GEQ, value)

    def <=(value: Double): Constraint = Constraint(terms, LEQ, value)
  }

  implicit class ModelBuilder(objective: Objective) {
    def subjectTo(constraints: Constraint*): Model = Model(constraints.toSet, Some(objective))
  }

  def max(terms: TermSet) = Objective(terms, MAX)

  def min(terms: TermSet) = Objective(terms, MIN)
}
