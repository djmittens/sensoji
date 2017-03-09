package services

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import me.ngrid.sensoji.rpc.RpcMicroservice

class OdysseyService extends Service[Request, Response]{
  def ping(): String = "Pong"
  def pong(): String = "Ping"
}
