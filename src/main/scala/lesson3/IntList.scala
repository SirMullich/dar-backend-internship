package lesson3

sealed trait IntList {
  def fold(end: Int, f: (Int, Int) => Int): Int = this match {
    case Pair(head, tail) =>
      f(head, tail.fold(end, f))

    case End => end
  }

  def map(f: (Int) => Int): IntList = this match {
    case Pair(head, tail) =>
      Pair(f(head), tail.map(f))
    case End => End
  }
}
case object End extends IntList
case class Pair(head: Int, tail: IntList) extends IntList
