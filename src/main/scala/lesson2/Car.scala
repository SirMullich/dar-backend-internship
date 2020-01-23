package lesson2

object Car {
  // all helper methods for Car
  def compareCar(car1: Car, car2: Car): Boolean = car1.getYear > car2.getYear

  def apply(model: String, year: Int): Car = apply(model, year, "Red")
  def apply(model: String, year: Int, color: String): Car = {
    if (model == "X5") {
      // i don't like x5
      new Car("X6", year, color)
    } else {
      new Car(model, year, color)
    }
  }
}

// default: private val for parameter
class Car private(var model: String, year: Int, color: String = "Red") {


  def getYear: Int = this.year

  def getModel: String = model

  def setModel(newModel: String): Unit = {
    this.model = newModel
  }

  override def hashCode(): Int = model.length + year + color.length
}
