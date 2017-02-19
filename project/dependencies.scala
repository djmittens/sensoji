import sbt._
import sbt.Keys._

object dependencies {
  val FinagleVersion = "6.41.0"

  lazy val finagleCore = Seq(
    resolvers += Resolver.url("Twitter maven" , url("http://maven.twttr.com/")),
    libraryDependencies += "com.twitter" %% "finagle-core" % FinagleVersion,
    libraryDependencies += "com.twitter" %% "finagle-thrift" % FinagleVersion
  )

  lazy val finagleServer = Seq(
    libraryDependencies += "com.twitter" %% "twitter-server" % "1.27.0",
      libraryDependencies += "com.twitter" %% "finagle-stats" % FinagleVersion
  )
}
