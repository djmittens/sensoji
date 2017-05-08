package me.ngrid.sensoji.http

import com.twitter.finagle.http.Request

trait RequestRouter {
  def route(req: Request)
}

trait PathBasedRouter {
}