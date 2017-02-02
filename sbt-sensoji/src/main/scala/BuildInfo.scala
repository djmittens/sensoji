import sbt._
import sbt.Keys._

object BuildInfo {
  val properties: SettingKey[Map[String, String]] = settingKey[Map[String, String]]{
    "Properties for the project, such as name and version"
  }
  val infoFile: TaskKey[File] = taskKey[File] {
    "Generated build information file for the project"
  }

  val settings = Seq(
    properties := Map.empty,
    properties += "version" -> version.value,
    properties += "name" -> name.value,
    infoFile := {
      makeInfo(
        (sourceManaged in Compile).value / "BuildInfo.scala",
        properties.value
      )
    },

    sourceGenerators in Compile += infoFile.map(x => Seq(x.asFile)).taskValue
  )

  def makeInfo(file: File, props: Map[String, String]): File = {

    val lines = for{
      (key, value) <- props
    } yield s"""val $key = "$value""""

    val source =
      s"""object BuildInfo {
         |${lines mkString "\n"}
         |}
     """.stripMargin

    IO.write(file, source)
    file
  }
}
