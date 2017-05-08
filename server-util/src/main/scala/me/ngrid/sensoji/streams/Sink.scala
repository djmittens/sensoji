package me.ngrid.sensoji.streams

trait Sink[T] {
  def apply(value: T): Unit
}
