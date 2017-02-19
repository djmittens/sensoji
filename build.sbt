val Organization = "me.ngrid.sensoji"

lazy val root = (project in file(".")).settings(
  scalaVersion := "2.12.1",
  scalacOptions in (ScalaUnidoc, unidoc) += "-Ymacro-no-expand",
  libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
).enablePlugins(ScalaUnidocPlugin)
  .aggregate(`rpc-server`, odyssey, `sbt-sensoji`)

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}
lazy val odyssey = sensoji.RPCService("odyssey", Organization).
  settings(dependencies.finagleHttp).
  enablePlugins(JavaAppPackaging).
  dependsOn(`rpc-server`)

lazy val `rpc-server` = (project in file("rpc-server")).
  settings(
    name := "rpc-server",
    organization := Organization,
    description := "Utilities for building rpc microservices",
    scalaVersion := "2.12.1"
  ).settings(dependencies.finagleCore ++ dependencies.finagleServer).
  enablePlugins(JavaAppPackaging)

lazy val `sbt-sensoji` = (project in file("sbt-sensoji")).settings(
  scalaVersion := "2.12.1"
)