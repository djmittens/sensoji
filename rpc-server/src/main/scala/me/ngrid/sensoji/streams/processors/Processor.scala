package me.ngrid.sensoji.streams.processors

trait Processor[In, Out] {
  def filter(f: Out => Boolean): Processor[In, Out] =
    (msg: In) => {
      val m = process(msg)
      if(f(m)) m else throw new MatchError("Message did not get past a filter")
    }

  def map[T](f: Out => T): Processor[In, T] =
    (msg: In) => {
      f(process(msg))
    }

  def flatMap[T](f: Out => Processor[Out, T]): Processor[In, T] =
    (msg: In) => {
      val m = process(msg)
      f(m).process(m)
    }

  def foreach(f: Out => Unit): Processor[In, Out] =
    (msg: In) => {
      val m = process(msg)
      f(m)
      m
    }

  def process(msg: In): Out
}

case class NoOpProcessor[T]() extends Processor[T, T] {
  override def process(msg: T): T = msg
}