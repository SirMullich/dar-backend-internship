package lesson6

import java.io.InvalidClassException

import akka.actor.typed.ActorSystem

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}
import scala.concurrent.duration._   // seconds, minutes, etc.

object Boot extends App {

  def divide(x: Int, y: Int): Int = try {
    x / y
  } catch {
    case e: InvalidClassException =>
      println("Error when dividing")
      0
    case e: IllegalArgumentException => 8
    case e: Throwable => 10
  }

  def divideFunctional(x: Int, y: Int): Try[Int] = Try(x / y)

  println(divide(8, 4))
  println(divide(8, 0))
  println(divideFunctional(8, 0))

  def readInt(str: String): Option[Int] =
    if(str matches "\\d+") Some(str.toInt) else None

  val either1: Either[String, Int] = Right(10)
  val either2: Either[String, Int] = Left("Failed to connect to DB")

  divideFunctional(19, 12).toOption

  val either3: Either[String, Int] = readInt("1o23").toRight("This is left")
  println(either3)

  either3.toOption

  val example1 = for {
    result1 <- readInt("189")                       // Option
    result2 <- divideFunctional(100, result1).toOption  // Option
  } yield result2

  println(example1)


  /**
   * Future (for async call)
   * new Threads, parallel programming, etc.
   */

  import scala.concurrent.ExecutionContext.Implicits.global // default Scala ec
  def sum(x: Int, y: Int): Future[Int] = Future { // new Thread
    x + y
  }

  def subtract(x: Int, y: Int): Future[Int] = Future {x - y}

  def multiple(x: Int, y: Int): Future[Int] = Future {
    Thread.sleep(5000)
    x * y
  }

  val res1: Future[Int] = for {
    num1 <- sum(100, 90)
    num2 <- multiple(10, num1)
    num3 <- subtract(num2, 43)
  } yield num3

//  res1.onComplete {
//    case Success(value) =>
//      println(s"Future result: $value")
//    case Failure(failure) =>
//      println(s"Failed: ${failure.getMessage}")
//  }

  // Bad practice
//  val res2 = Await.result(res1, 100.hours)     // block

//  Thread.sleep(6000)
//  println("Program finished")


  val system: ActorSystem[HelloWorldMain.SayHello] = ActorSystem(HelloWorldMain(), "hello")
  system ! HelloWorldMain.SayHello("World")
  system ! HelloWorldMain.SayHello("Akka")
  system ! HelloWorldMain.SayHello("Test")

}