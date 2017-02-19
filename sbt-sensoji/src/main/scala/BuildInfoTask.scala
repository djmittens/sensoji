import sbt._
import sbt.Keys._


object BuildInfoTask {
  lazy val properties: SettingKey[Map[String, String]] = settingKey[Map[String, String]]{
    "Properties for the project, such as name and version"
  }
  lazy val infoFile: TaskKey[File] = taskKey[File] {
    "Generated build information file for the project"
  }

  def settings = Seq(
    properties := Map.empty,
    properties += "version" -> version.value,
    properties += "name" -> name.value,
    infoFile := {
      makeInfo(
        (sourceManaged in Compile).value / "BuildInfoTask.scala",
        properties.value
      )
    },

    sourceGenerators in Compile += infoFile.map(x => Seq(x.asFile)).taskValue
  )

  def makeInfo(file: File, props: Map[String, String]): File = {
    println(props)

    lazy val lines = props.map(a => s"""val ${a._1} = "${a._2}"""")

    val source =
      s"""object BuildInfo {
         |  ${lines mkString "\n"}
         |}
     """.stripMargin

    IO.write(file, source)
    file
  }
}
