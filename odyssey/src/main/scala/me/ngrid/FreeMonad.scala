package me.ngrid


import scala.annotation.tailrec
import scala.language.{higherKinds, implicitConversions}

object FreeMonad {

  implicit def step[A](a: => A): Trampoline[A] = More[Function0, A](() => Done(a))

  def main(args: Array[String]): Unit = {
    val hello: Trampoline[Unit] = for {
      _ <- println("hello ")
      _ <- println("world")
    } yield ()

    println(runS(hello zaaap hello))
  }

  def runS[A](f: Free[Function0, A]): A = f.resume match {
    case Right(a) => a
    case Left(k) => runS(k())
  }

  type Trampoline[+A] = Free[Function0, A]

  sealed trait Free[S[+ _], +A] {

    @tailrec
    final def resume(implicit S: Functor[S]): Either[S[Free[S, A]], A] = this match {
      case Done(a) => Right(a)
      case More(k) => Left(k)
      //      case k@FlatMap(_: Free[S, A], _:Function1[A,Free[S, A]]) => k.resumeF
      case FlatMap(a, f) =>
        a match {
          case Done(i) => f(i).resume
          case More(l) => Left(S.map(l)(_.flatMap(f)))
          case FlatMap(b, g) =>
            b.flatMap((x: Any) => g(x).flatMap(f)).resume
        }
    }

    final def flatMap[B](f: A => Free[S, B]): Free[S, B] = FlatMap(this, f)

    final def map[B](f: A => B): Free[S, B] = flatMap((a) => Done(f(a)))

    final def zaaap[B](f: Free[S, B])(implicit S: Functor[S]): Free[S, (A, B)] = (resume, f.resume) match {
      case (Left(a), Left(b)) =>
        More(S.map(a) { x =>
          More(S.map(b)(y => x zaaap y))
        })

      case (Left(a), Right(b)) =>
        More(S.map(a)(x => x zaaap Done(b)))

      case (Right(b), Left(a)) =>
        More(S.map(a)(x => Done(b) zaaap x ))

      case (Right(a), Right(b)) =>
        Done(a -> b)

    }
  }

  final case class FlatMap[S1[+ _], A1, +B]
  (
    a: Free[S1, A1],
    f: A1 => Free[S1, B]
  ) extends Free[S1, B]

  final case class Done[S[+ _], +A](a: A) extends Free[S, A]

  final case class More[S[+ _], +A](k: S[Free[S, A]]) extends Free[S, A]


  trait Functor[F[_]] {
    def map[A, B](m: F[A])(f: A => B): F[B]
  }

  object Functor {
    implicit val f0Functor: Functor[Function0] = new Functor[Function0] {
      override def map[A, B](m: () => A)(f: (A) => B): () => B = () => f(m())
    }
  }

}

