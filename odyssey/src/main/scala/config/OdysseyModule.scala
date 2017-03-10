package config

import com.twitter.finagle.{Http, ListeningServer}
import me.ngrid.sensoji.rpc.RpcMicroservice
import services.OdysseyService

trait OdysseyModule {
  lazy val odysseyServer: RpcMicroservice = RpcMicroservice (pingPongServer)
  lazy val pingPongServer: ListeningServer = Http.server.serve(":8080", pingPongService)
  lazy val pingPongService = new OdysseyService()
}
