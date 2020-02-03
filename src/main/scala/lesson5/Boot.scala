package lesson5

object Boot extends App {
  val myList: List[Int] = List(9, 1, -8, 0, 1, 8, 4, 2, 5, 1)

  // f(9, f(1, f(-8, ... f(1, 100))))))


  // zero-element: Int
  // func: (Int, Int) => Int
  println(myList.fold(100)((a, b) => a + b))

  // zero-element: B
  // func: (B, Int) => B

  // MySum(0) <- 9, 1, -8, ....
  // 1) MySum(0)
  // 2) MySum(0), 9 => MySum(9)
  // 3) MySum(9), 1 => MySum(10)
  // 4) MySum(10), -8 => MySum(2)
  // ...
  println(myList.foldLeft(MySum(0))((a, b) => MySum(a.amount + BigDecimal(b))))
  println(myList.foldLeft(100)((a, b) => a + b))

  println(myList.foldRight(MySum(0))((a, b) => MySum(BigDecimal(a) + b.amount)))

  println(MySum(myList.sum))

  val nestedList: List[List[Int]] = List(List(1, 2, 3), List(0, -6, 8, 82), List(0))

  println(nestedList.flatten)

  // abc -> abc, acb, bac, bca, cab, cba

  // map -> flatten = flatMap
  println(List("a", "aupoi", "zes").flatMap{str => str.permutations.toList})


  /**
   * For comprehension
   */
  val iter: List[Int] = for {
    num <- myList
  } yield 2 * num

  println(iter)


  val ex2 = for {
    num <- myList // List[Int]
    num2 <- myList.map(x => x + 10).filter(_ % 2 == 0) // _ = any
  } yield {
    val num3 = num + 9
    8 * num - num2 + 100 * num3
  }

  println(ex2.length)
  println(ex2)


  /**
   * Monads
   */

  /**
   * Option (aka null improvement)
   */

  def getSum(mySum: MySum): BigDecimal = {
    mySum.amount
  }

  val x = null
  val y = null

  if (x != null) {
    println(getSum(x))

    if (y != null) {
      println("ok")
    } else {
      //
    }
  } else {
    if (y != null) {

    }
  }

  val opt1: Option[Int] = Some(8) // Success
  val opt2: Option[Int] = None    // Failure
  val opt3: Option[Int] = Some(10)
  val opt4: Option[Int] = Some(5)

  def processOption(opt: Option[Int]): String = opt match {
    case Some(value) => s"There is: ${value}"
    case None => "There is None"
  }

  println(processOption(opt1))
  println(processOption(opt2))

  val optResult: Option[Int] = for {
    value1 <- opt1
//    value2 <- opt2
    value3 <- opt3
    value4 <- opt4
  } yield value1 + value3 + value4  // + value2

  println(processOption(optResult))

  // regex
  def readInt(str: String): Option[Int] =
    if(str matches "\\d+") Some(str.toInt) else None


  println(readInt("20983745").map(x => x * 2))
  println(readInt("2098uju3745").map(x => x * 2).filter(_ == 98))
  println(readInt("50").map(x => x * 2).filter(_ == 98))

  val result = readInt("8934").flatMap { num1 => // Int => Option[Option[Int]]
    readInt("98p0").flatMap { num2 => // Int => Option[Int]
      readInt("803456").map { num3 => // Int => Int
        println("Calculating sum")
        num1 + num2 + num3
      }
    }
  }

  println(s"result is $result")

  /**
   * Either
   */
  val either1: Either[String, Int] = Right(10)
  val either2: Either[String, Int] = Left("Failed to connect to DB")
  val either3: Either[String, Int] = Right(100)
  val either4: Either[String, Int] = Left("Failed to convert JSON")
  var either5: Either[MySum, Int] = Left(MySum(-1000))
  either5 = Right(1900)

  println(either5.isRight)
  println(either5.isLeft)

  val eitherResult: Either[String, Int] = for {
    value1 <- either1
    value2 <- either2
    value3 <- either3
    value4 <- either4
  } yield value1 * value2 * value3 + value4

  println(eitherResult match {
    case Right(value) => s"Successful operation result: $value"
    case Left(error) => s"Error when making operation: $error"
  })
}