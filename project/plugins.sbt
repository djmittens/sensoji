addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.4.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.4")
//addSbtPlugin(RootProject(file("")))
//lazy val root = (project in file(".")).dependsOn(sbtSensoji)
lazy val plugins = (project in file(".")).dependsOn (file("../sbt-sensoji"))


//lazy val sbtSensoji = ProjectRef(uri("git://../"), "sbt-sensoji")
