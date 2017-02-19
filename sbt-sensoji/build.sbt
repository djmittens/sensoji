lazy val `sbt-sensoji` = (project in file(".")).settings(
  sbtPlugin := true,
  name := "sbt-sensoji",
  organization := "me.ngrid.sensoji",
  description := "SBT plugin for writing microservices",
  scalaVersion := "2.10.5",
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-language:_",
    "-target:jvm-1.6",
    "-encoding", "UTF-8"
  )
)
