//addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.4.0")
//addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.4")
//addSbtPlugin(RootProject(file("")))
//lazy val root = (project in file(".")).dependsOn(sbtSensoji)
//scalaVersion := "2.12.1"
//libraryDependencies += "org.scala-sbt" % "scripted-plugin" % sbtVersion.value
lazy val plugins = (project in file(".")).
  dependsOn (file("../sbt-sensoji"))

// Example of using github dependency
//lazy val plugins = (project in file(".")).dependsOn(sj)
//lazy val sj = ProjectRef(uri("git://github.com/djmittens/sensoji.git"), "sensojiplugin")
