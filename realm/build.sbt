lazy val realm = (project in file(".")).settings(
  name := "realm",
  organization := "sbtdocker",
  version := "1.0",
  scalaVersion := "2.12.1"
).enablePlugins(sbtdocker.DockerPlugin, JavaAppPackaging)

dockerfile in docker := {
  val appDir = stage.value

  val targetDir = "/app"

  new Dockerfile {
    from("java")
    entryPoint(s"$targetDir/bin/${executableScriptName.value}")
    copy (appDir, targetDir)
  }
}

buildOptions in docker := BuildOptions(cache = false)