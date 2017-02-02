//lazy val sbtSensoji = (project in file("sbt-sensoji"))
lazy val sbtSensoji = project in file("sbt-sensoji")

//SensojiPlugin

lazy val realm = project in file("realm")
//lazy val realm = sensoji.common.MicroService("realm")
lazy val client = project in file("client")

lazy val odyssey = common.MicroService("odyssey")
