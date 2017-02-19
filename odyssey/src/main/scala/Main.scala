import com.twitter.finagle.Http
import com.twitter.finagle.http.{Request, Response, Status, Version}
import com.twitter.util.Future
import me.ngrid.sensoji.rpc.RpcMicroservice

/**
  * Moar documentation
  */
object Main extends App {
  lazy val server = new RpcMicroservice (
    Http.server.serve("localhost:8080", { req: Request =>
      Future {
        val res = Response(Version.Http11, Status.Ok)
        res.setContentString("Hello World !")
        res
      }
    })
  )

  server.main(args)
}
