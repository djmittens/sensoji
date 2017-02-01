import sbt._
import Keys._

object common {
  val commonSettings = BuildInfo.settings ++ Seq(
    organization := "me.ngrid"
  )

  def MicroService(name: String): Project = {
    Project(name, file(name)).settings(commonSettings)
  }
}
