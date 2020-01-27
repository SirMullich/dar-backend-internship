package lesson3

sealed trait Feline {
  def dinner: Food = this match {
    case Lion() => Antelope
    case Tiger() => TigerWoods
    case Panther() => Licorice
    case Cat(favouriteFood) => CatFood(favouriteFood)
  }
}

final case class Lion() extends Feline {
//  override def dinner: Food = Antelope
}
final case class Tiger() extends Feline {
//  override def dinner: Food = TigerWoods
}
final case class Panther() extends Feline {
//  override def dinner: Food = Licorice
}
final case class Cat(favouriteFood: String) extends Feline {
//  override def dinner: Food = CatFood(favouriteFood)
}
