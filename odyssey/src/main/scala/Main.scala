import config.profiles.DefaultProfile

/**
  * Moar documentation
  */
object Main {
  def main(args: Array[String]): Unit = {
    object profile extends DefaultProfile
    profile.odysseyServer.main(args)
    println("hello")
  }
}
