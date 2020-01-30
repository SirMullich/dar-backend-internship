package lesson4

object Boot extends App {
  val genericList: GenericList[String] = GenericPair("my string", GenericPair("123", GenericPair("Ivan", GenericEnd())))

  println(genericList)

  genericList match {
    case GenericPair(head, tail) =>
      println(s"Hi ${head.toUpperCase}")
      tail match {
        case GenericPair(h, t) =>
          println(s"Hi ${ h.toUpperCase}")

        case GenericEnd() =>
          println("finished")
      }
    case GenericEnd() => println("finished")
  }
  val upperCaseFunc = (str1: String, str2: String) => str1.toUpperCase + str2.toUpperCase
  println(s"Uppercase list: ${genericList.fold("end", upperCaseFunc)}")

  val myList = GenericPair(TempClass("Ivan", 12), GenericPair(TempClass("Therion", 25),
    GenericPair(TempClass("Stark", 30), GenericEnd())))

  val tempFunc = (temp1: TempClass, temp2: TempClass) =>
    TempClass(temp1.name + " " + temp2.name, temp1.age + temp2.age)

  println(s"TempClass fold: ${myList.fold(myList.head, tempFunc)}")


  /**
   * Collections
   */

  val seq1 = Seq(6, -10, 1, 40, 2, 5, 6, 10)
  println(seq1(4))
  println(seq1.head)
  println(seq1.tail)

  // find
  val myFunc1 = (x: Int) => x % 2 == 0 // even
  println(seq1.find(myFunc1)) // declared function
  println(seq1.find(x => x % 2 == 0)) // anonymous function
  // Option: Some(value) and None
  println(seq1.find(x => x == 1000))


  println(Seq("my", "name", "student", "computer").find(x => x == "name"))


  // filter
  println(seq1.filter(x => x % 2 == 0))

  // map
  println(seq1.map(x => x + 100))
  println(seq1.map(x => s"number is: $x, "))
  println(seq1.map(x => TempClass("Ivan", x)))

  // chaining operations (composition)
  println(seq1.filter(x => x % 2 == 0).map(x => s"This $x"))

//  val mutableList = scala.collection.mutable.

  println(seq1 :+ 87)
  println(87 +: seq1)

  println(seq1 ++ Seq(19, 80, 76, 15))

}