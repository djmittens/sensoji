import sbt._
import sbt.Keys._

object common {
  final val FinagleVersion = "6.41.0"

  def MicroService(name: String, desc: String = ""): Project = {
    Project(name, file(name)).settings(commonSettings ++ commonDependencies ++ finagleDependencies)
  }

  val commonSettings = BuildInfo.settings ++ Seq(
    organization := "me.ngrid",
    scalaVersion := "2.11.8"
  )

  val commonDependencies = Seq(
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
  )

  val finagleDependencies = Seq(
    resolvers += Resolver.url("Twitter maven" , url("http://maven.twttr.com/")),
    libraryDependencies += "com.twitter" %% "finagle-core" % FinagleVersion,
    libraryDependencies += "com.twitter" %% "finagle-thrift" % FinagleVersion
  )
}
