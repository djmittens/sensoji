package me.ngrid.sensoji.util

import com.twitter.util.{Awaitable, Closable}

trait SensojiTask[T] {
  def run(): RunningTask[T]
}

trait RunningTask[T] extends Closable with Awaitable[T]
