import sbt._

object common {
  val commonSettings = BuildInfo.settings ++ Seq(
//    organization := "me.ngrid"
  )

  def MicroService(name: String): Project = {
    Project(name, file(name)).settings(commonSettings)
  }
}
