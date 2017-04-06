package me.ngrid.sensoji.streams.processors

import com.twitter.util.Future

trait Source[K, V] {
  def start(p: Processor[Record[K, V], _]): Future[Unit]
}

case class Record[K, V](key: K, value: V)
