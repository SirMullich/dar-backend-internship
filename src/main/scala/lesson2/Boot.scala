package lesson2

object Boot extends App {


  object Test7 {
    val simpleField: Int = {
      println("Evaluating simpleField")
      42
    }
    def noParameterMethod: Int = {
      println("Evaluating noParameterMethod")
      42
    }
  }


  Test7.simpleField
  Test7.noParameterMethod
  Test7.simpleField
  Test7.noParameterMethod

  val car1 = Car("E-class", 2000)
  val car2 = Car("E-class", 2000)
//  car1.setModel("Camry")
  println(car1 == car2) // true or false?
  println(car1.equals(car2)) // why?
//  println(car2)

  val adder = new Adder(90)
  println(adder(10))
  println(adder("10"))

  val diff = Math.abs(-10 - 50)

  println(Car.compareCar(car1, car2))

  println(Car.apply("Model S", 2018))
  println(Car("X5", 2018).getModel)

  val lambo1 = SuperCar("Lambo", 2010, "Blue") // auto apply
  val lambo2 = SuperCar("Lambo", 2010, "Bluee")

  println(s"Lambos are equal: ${lambo1 == lambo2}")
  println(s"Lambo1 year: ${lambo1.year}")

  val lambo3 = lambo1.copy(year = 2020) // Lambo("Lambo", 2020, "Blue")
  println(lambo3)

  // dummy line to test git
}
