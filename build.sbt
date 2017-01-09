lazy val appName = "constraints-dsl"
lazy val appVersion = "1.0"

name := appName
version := appVersion
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
  "org.choco-solver" % "choco-solver" % "4.0.0.a",
  "com.github.pathikrit" %% "better-files" % "2.15.0"
)

assemblyJarName in assembly := s"$appName-$appVersion.jar"
