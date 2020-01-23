package lesson2

// only object (instance)
object Singleton {

  var singleton: Singleton = _

  def apply(num: Int): Singleton = if (singleton == null) {
    singleton = new Singleton(num)
    singleton
  } else {
    singleton.num = num
    singleton
  }
}

case class Singleton private(private var num: Int) {

}
