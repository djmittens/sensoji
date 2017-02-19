package me.ngrid.sensoji.rpc

import com.twitter.finagle.ListeningServer
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}

/**
  * Wooh documentation
  */
class RpcMicroservice(run: => ListeningServer) extends TwitterServer{

  /**
    * Whats wrong with this?
    */
  def main():Unit = {
    println("helloworl")
    Await.result(run)
  }
}
