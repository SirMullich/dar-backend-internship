package lesson1

object Boot extends App {

  println("Hello World")

  println("5 + 6 = " + add(5, 6))

//  println(add("Hello", "World"))

  val sum: Int = 6 + 10 + 11 // alt + '=' to show type
  val str = "My String"

//  val x = Math.pow(str, 100)

  for (i <- 1 to 3) {
    println(i)
  }

  // immutable
  val age = 50
  val prefix = if (age > 40) "Mr." else "Pal "

  // mutable
  var prefix2 = ""
  if (age > 40) {
    prefix2 = "Mr."
  } else {
    prefix2 = "Pal"
  }

  def add(x: Int, y: Int) = x + y

  // ABA problem


  //   memory cell    100  100  100  100  100  100    300        100        100   100  100

  //                   A    A    A    A    A    A      B (+200)   C (-200)   A     A    A     A


  // IMMUTABILITY
  println("hello".toUpperCase)

  println(2.min(9))

  def add2(x: Int, y: Int) = {
    def add3(x: Int, y: Int) = {
      def add4(x: Int, y: Int) = x + y
      add4(x, y)
    }

    10 + add3(x, y)
  }

  println(add2(100, 90))

  val subtract: (Int, Int) => Int = (x: Int, y: Int) => x - y

  val multiply = (x: Int, y: Int) => x * y

  // Higher-order function
  val applyFunc = (f: (Int, Int) => Int, x: Int, y: Int) => f(x, y)

  println(applyFunc(subtract, 90, 10))
  println(applyFunc(multiply, 90, 10))

  subtract(900, applyFunc(multiply, subtract(200, 100), add2(10, 800)))

  assert(multiply(5, 6) == 30)
  assert(multiply(5, 0) == 0)
  // ip:port,ip2:port2,ip3:port3,ip4


  val subtract2: (Int, Int) => Unit = (x: Int, y: Int) => {
    // connect to Database

    // connect to Pentagon             // Side effect

    // sends SMS to mother             // Side effect

    // calls to colleague
  }

  assert(subtract2(8, 9) == ())

  // f(x) = x * x           // val x
  // f(9) = 9 * 16


  def greet(name: String) = {
    println("Hi, " + name)
  }

  greet("Daulet")
  greet("Saken")


  def greet2(name: String) = s"Hi $name" // string interpolation

  assert(greet2("Daulet") == "Hi Daulet")
  println(greet2("Saken"))

}