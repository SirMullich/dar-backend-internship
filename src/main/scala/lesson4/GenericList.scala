package lesson4

// variance short: A is a subtype of B
// is GenericList[A] a GenericList[B]
// or GenericList[B] is a GenericList[A]

sealed trait GenericList[T] {
  def fold(end: T, f: (T, T) => T): T = this match {
    // Alt + '='
    case GenericPair(head, tail) =>
      f(head, tail.fold(end, f))
    case GenericEnd() => end
  }

  // TODO: implement map [T, B]  f: T => B
}
case class GenericEnd[T]() extends GenericList[T]
case class GenericPair[T](head: T, tail: GenericList[T]) extends GenericList[T]