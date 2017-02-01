//lazy val realm = project in file("realm")
lazy val realm = common.MicroService("realm")
lazy val client = project in file("client")

