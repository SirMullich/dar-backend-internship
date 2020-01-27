package lesson3

sealed trait IntList
case object End extends IntList
case class Pair(head: Int, tail: IntList) extends IntList
