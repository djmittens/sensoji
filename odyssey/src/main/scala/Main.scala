import com.twitter.finagle.Http
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future
import me.ngrid.sensoji.rpc.RpcMicroservice

/**
  * Moar documentation
  */
object Main {
  lazy val server = new RpcMicroservice (
    Http.server.serve("localhost:8080", { req: Request =>
      Future {
        val res = Response()
        res.setContentString("Hello World !")
        res
      }
    })
  )

  def main(args: Array[String]): Unit = {
    server.main(args)
  }
}
