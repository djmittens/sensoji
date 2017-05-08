package me.ngrid.sensoji.rpc

import com.twitter.finagle.ListeningServer
import com.twitter.server.TwitterServer
import com.twitter.util.Await

/**
  * Wooh documentation
  */
class RpcMicroservice(run: => ListeningServer) extends TwitterServer {

  /**
    * Whats wrong with this?
    */
  def main():Unit = {
    log.info(s"Serving on ${run.boundAddress.toString}")
    Await.result(run)
  }
}

object RpcMicroservice {
  def apply(run: => ListeningServer) = new RpcMicroservice(run)
}
