import sbt._

object dependencies {
  val FinagleVersion = "6.42.0"

  lazy val twitterResolver: URLRepository = Resolver.url("Twitter maven", url("http://maven.twttr.com/"))

  lazy val finagleCore = Seq(
    "com.twitter" %% "finagle-core" % FinagleVersion,
    "com.twitter" %% "finagle-thrift" % FinagleVersion
  )

  lazy val finagleServer = Seq(
    "com.twitter" %% "twitter-server" % "1.27.0",
    "com.twitter" %% "finagle-stats" % FinagleVersion
  )

  lazy val finagleHttp = Seq(
    "com.twitter" %% "finagle-http" % FinagleVersion
  )

  lazy val sensojiServer: Seq[ModuleID] =
    (finagleCore ++ finagleServer).map(_ % "provided")
}
