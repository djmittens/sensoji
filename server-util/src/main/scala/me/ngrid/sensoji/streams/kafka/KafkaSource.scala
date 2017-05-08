package me.ngrid.sensoji.streams.kafka

import com.twitter.util.Future
import me.ngrid.sensoji.streams.{Processor, Source}
import me.ngrid.sensoji.streams.processors.{Record, Source}

class KafkaSource[K, V] extends Source[K, V]{
  override def start(p: Processor[Record[K, V], _]): Future[Unit] = {
  }
}
