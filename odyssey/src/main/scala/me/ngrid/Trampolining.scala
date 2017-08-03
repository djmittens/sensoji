package me.ngrid

import scala.annotation.tailrec

object Trampolining {

  import State._
  import Trampoline._

  def main(args: Array[String]): Unit = {

    def p[A](x: List[A]): Unit = {
      println(zipIndex[A](x))
    }

    println(even((0 until 10).toList).runT)
    println(odd((0 until 1000001).toList).runT)
    p((0 to 10).toList)
    p((0 to 10000).toList)

    val hello: Trampoline[Unit] = for {
      _ <- print("Hello, ")
      _ <- println("World!")
    } yield ()

    (hello zip hello).runT
  }



  def zipIndex[A](as: List[A]): List[(Int, A)] = {
    as.foldLeft(
      pureState[Int, List[(Int, A)]](List())
    )((acc, a) => for {
      xs <- acc
      n <- getState
      _ <- setState(n + 1)
    } yield (n, a) :: xs).
      runS(0).runT._1.reverse
  }

  def even[A](ns: List[A]): Trampoline[Boolean] = ns match {
    case Nil => Done(true)
    case _ :: xs => More(() => odd(xs))
  }

  def odd[A](ns: List[A]): Trampoline[Boolean] = ns match {
    case Nil => Done(false)
    case _ :: xs => More(() => even(xs))
  }

}

object State {
  def getState[S]: State[S, S] = State(s => Done(s -> s))

  def setState[S](s: S): State[S, Unit] = State(_ => Done(() -> s))

  def pureState[S, A](a: A): State[S, A] = State(s => Done(a -> s))
}

case class State[S, A](runS: S => Trampoline[(A, S)]) {
  def map[B](f: A => B): State[S, B] =
    State[S, B](s => {
      runS(s).map {
        case (a, s1) => f(a) -> s1
      }
    })

  def flatMap[B](f: A => State[S, B]): State[S, B] =
    State[S, B](s => More(() => {
      runS(s) flatMap {
        case (a, s1) => More(() => f(a) runS s1)
      }
    }))
}

object Trampoline {
  implicit def step[A](a: => A): Trampoline[A] = More(() => Done(a))
}

sealed trait Trampoline[+A] {
  @tailrec
  final def runT: A = this match {
    case More(k) => k().runT
    case Done(v) => v
    case k @ FlatMap(_, _) =>
      k.resume match {
        case Right(v) => v
        case Left(k) => k().runT
      }
  }

  @tailrec
  final def resume: Either[() => Trampoline[A], A] = this match {
    case Done(v) => Right(v)
    case More(k) => Left(k)
    case FlatMap(a, f) => a match {
      case Done(v) => f(v).resume
      case More(k) => Left(() => FlatMap(k(), f))
      case FlatMap(b, g) =>
        (FlatMap(b, (x: Any) => FlatMap(g(x), f)): Trampoline[A]).resume
    }
  }

  def map[B](f: A => B): Trampoline[B] = flatMap(x => Done(f(x)))

  final def flatMap[B](f: A => Trampoline[B]): Trampoline[B] = this match {
    case FlatMap(a, g) => FlatMap(a, (x: Any) => g(x) flatMap f)
    case x => FlatMap(x, f)
  }

  final def zip[B](b: Trampoline[B]): Trampoline[(A, B)] =
    (this.resume, b.resume) match {
      case (Right(a), Right(b)) => Done((a, b))
      case (Left(a), Left(b)) => More(() => a() zip b())
      case (Left(a), Right(b)) => More(() => a() zip Done(b))
      case (Right(a), Left(b)) => More(() => Done(a) zip b())
    }
}

sealed case class FlatMap[A, +B] (sub: Trampoline[A], k: A => Trampoline[B]) extends Trampoline[B]

case class More[+A](k: () => Trampoline[A]) extends Trampoline[A]

case class Done[+A](result: A) extends Trampoline[A]
