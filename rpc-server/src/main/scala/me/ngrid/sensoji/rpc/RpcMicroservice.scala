package me.ngrid.sensoji.rpc

import com.twitter.finagle.ListeningServer
import com.twitter.server.TwitterServer
import com.twitter.util.Await

/**
  * Wooh documentation
  */
class RpcMicroservice(run: => ListeningServer) extends TwitterServer{

  /**
    * Whats wrong with this?
    */
  def main():Unit = {
    Await.result(run)
  }
}
