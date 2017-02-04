package components

object Dsl {
  implicit def stringToVariable(name: String): Variable = Variable(name)

  implicit class IntegerToTermConverter(prefix: Int) extends ((String) => Term) {
    def apply(name: String): Term = Term(prefix, Variable(name))
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

  def max(terms: TermSet) = Objective(terms, MAX)

  def min(terms: TermSet) = Objective(terms, MIN)
}
