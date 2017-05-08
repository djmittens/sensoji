package me.ngrid.sensoji.streams

import me.ngrid.sensoji.util.RunningTask

trait Source[T] {
  def runWith(sink: Sink[T]): RunningTask[Unit]
}
