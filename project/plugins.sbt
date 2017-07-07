addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.4.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.4")
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.4.0")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.5")
val rt = (project in file(".")).dependsOn(RootProject(file("../sbt-sensoji")))
// Example of using github dependency
//lazy val plugins = (project in file(".")).dependsOn(sj)
//lazy val sj = ProjectRef(uri("git://github.com/djmittens/sensoji.git"), "sensojiplugin")
