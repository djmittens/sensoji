lazy val sbtplugin = (project in file(".")).settings(
  name := "sbt-sensoji",
  organization := "me.ngrid",
  description := "SBT plugin for writing microservices",
  sbtPlugin := true
)