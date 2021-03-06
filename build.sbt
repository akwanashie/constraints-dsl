lazy val appName = "constraints-dsl"
lazy val appVersion = "1.0"

name := appName
version := appVersion
scalaVersion := "2.11.8"
coverageEnabled := true

mainClass in Compile := Some("io.console.Console")

resolvers ++= Seq(
  "spongepowered" at "https://repo.spongepowered.org/maven/",
  "Artima Maven Repository" at "http://repo.artima.com/releases"
)

val devDependenxies = Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
  "org.mockito" % "mockito-core" % "2.7.2" % "test"
)

val liveDependencies = Seq(
  "org.choco-solver" % "choco-solver" % "4.0.1",
  "com.github.pathikrit" %% "better-files" % "2.15.0",
  "org.jline" % "jline" % "3.1.2"
)

libraryDependencies ++= (devDependenxies ++ liveDependencies)

assemblyJarName in assembly := s"$appName-$appVersion.jar"

coverageEnabled := true
