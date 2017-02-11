import components.Dsl._

def createAndSaveModel() = {
  val model = max(1("a") + 1("b")) subjectTo(
      2("a") + 1("b") <= 2,
      1("a") + 1("b") >= 1
    )
  model.save("/var/tmp/constraints-dsl/temp.lp")
}

createAndSaveModel()