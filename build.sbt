val Organization = "me.ngrid.sensoji"

//TODO: Add publishing settings.
lazy val root = (project in file(".")).settings(
  scalaVersion := "2.12.1",
  scalacOptions in(ScalaUnidoc, unidoc) += "-Ymacro-no-expand",
  publishArtifact := false,
  libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
).enablePlugins(ScalaUnidocPlugin)
  .aggregate(`server-util`, odyssey)

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}
lazy val odyssey = sensoji.RPCService("odyssey", Organization).
  settings(libraryDependencies ++= dependencies.finagleHttp).
  enablePlugins(JavaAppPackaging).
  dependsOn(`server-util`)

lazy val `server-util` = (project in file("server-util")).
  settings(
    name := "rpc-server",
    organization := Organization,
    description := "Utilities for building rpc microservices",
    scalaVersion := "2.12.1"
  ).settings(libraryDependencies ++= dependencies.sensojiServer).
  settings(libraryDependencies += dependencies.catsLibrary).
  enablePlugins(JavaAppPackaging)

val `sbt-sensoji` = RootProject(file("sbt-sensoji"))
