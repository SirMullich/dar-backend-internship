package lesson2

class Adder(amount: Int) {
  // applied by default
  def apply(in: Int): Int = in + amount

  def apply(num: String): Int = num.toInt + apply(num.toInt)
}
