package config

import com.twitter.finagle.Http
import services.OdysseyService

trait OdysseyModule {
  lazy val odysseyServer = Http.server.serve(":8080", pingPongService)
  lazy val pingPongService = new OdysseyService()
}
