package services

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future

class OdysseyService extends Service[Request, Response]{
  override def apply(request: Request): Future[Response] = {
    val r = Response()
    r.setContentString("Pong")
    Future(r)
  }
}
