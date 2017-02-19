import sbt.Keys._
import sbt._

object sensoji {
  val SensojiVersion = "0.1-SNAPSHOT"

  def RPCService(name: String, org: String, desc: String = "RPC Microservice built with Sensoji"): Project = {
    Project(name, file(name)).
      settings(
        organization := org
      ).
      settings(commonSettings ++ commonDependencies ++ rpcServiceSettings)
  }

  lazy val commonSettings: Seq[Setting[_]] = Seq(
    scalaVersion := "2.12.1"
  ) ++ BuildInfoTask.settings

  lazy val commonDependencies = Seq(
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
  )

  lazy val rpcServiceSettings = Seq(
    libraryDependencies += "me.ngrid" %% "rpc-server" % SensojiVersion
  )
}
