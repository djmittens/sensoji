package me.ngrid.free

import scala.annotation.tailrec
import scala.language.higherKinds

object FreeMonad {
  type Trampoline[+A] = Free[Function0, A]


  def main(args: Array[String]): Unit = {

  }
}

sealed trait Free[S[+ _], +A] {

  private case class FlatMap[S1[+_], A1, +B]
  (
    a: Free[S1, A1],
    f: A1 => Free[S1, B]
  ) extends Free[S1, B]

  @tailrec
  final def resume(implicit S: Functor[S]) : Either[S[Free[S, A]], A] = this match {
    case Done(a) => Right(a)
    case More(k) => Left(k)
    case FlatMap(a, f) => a match {
      case Done (i) => f(i).resume
      case More(l) => Left(S.map(l)(_ flatMap f))
      case b FlatMap g => b.flatMap((x: Any) => g(x) flatMap f).resume
    }
  }

  def flatMap[B](f: A => Free[S, B]): Free[S, B] = FlatMap(this, f)

}

case class Done[S[+_], +A](a: A) extends Free[S, A]
case class More[S[+_], +A](k: S[Free[S, A]]) extends Free[S, A]


trait Functor[F[_]] {
  def map[A, B](m: F[A])(f: A => B): F[B]
}

object Functor {
  implicit val f0Functor = new Functor[Function0] {
    override def map[A, B](m: () => A)(f: (A) => B): () => B = () => f(m())
  }
}
