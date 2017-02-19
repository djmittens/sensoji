val Organization = "me.ngrid.sensoji"

lazy val odyssey = sensoji.RPCService("odyssey", Organization).dependsOn(`rpc-server`)

//javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}

lazy val `rpc-server` = (project in file("rpc-server")).
  settings(
    name := "rpc-server",
    organization := Organization,
    description := "Utilities for building rpc microservices",
    scalaVersion := "2.12.1"
  ).settings(dependencies.finagleCore ++ dependencies.finagleServer).
  enablePlugins(JavaAppPackaging)


lazy val `sbt-sensoji` = project in file("sbt-sensoji")