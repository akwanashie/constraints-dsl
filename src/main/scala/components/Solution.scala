package components

case class Solution(variables: Set[Variable], objectiveValue: Option[Double]) {
  override def toString: String = {
    val values = variables.map { variable =>
      s"${variable.name}=${variable.value}"
    } mkString " "

    s"""
       |Objective: ${objectiveValue.getOrElse(-1)}
       |Values: $values
     """.stripMargin
  }
}
