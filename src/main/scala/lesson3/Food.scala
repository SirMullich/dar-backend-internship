package lesson3

sealed trait Food
final case object Antelope extends Food
final case object TigerWoods extends Food
final case object Licorice extends Food
final case class CatFood(food: String) extends Food