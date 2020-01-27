package lesson3

case class Person(firstName: String, lastName: String)

object Boot extends App {

  val person1 = Person("Peter", "Parker")
  val person2 = Person("Jamie", "Lannister")
  val person3 = Person("Bruce", "Wayne")


  def greetPerson(person: Person): String = person match {
    case Person("Peter", "Parker") => "Hello spider-man!"
    case Person("Jamie", "Lannister") => "Hello Kingslayer!"
    case Person("Bruce", "Wayne") => "Hello Batman!"
    case Person(name, surname) if name.length < 5 => s"Hello, ${name}. You have a such a short name"
    case Person(name, surname) => s"Hello, ${name}"
    case any: Any => s"Unexpected person?"
  }

  println(greetPerson(person2))
  println(greetPerson(person3))

  println(greetPerson(Person("Joker", "Fleck")))
  println(greetPerson(Person("Amy", "Fleck")))


  val cassandraRepo = CassandraRepo()

//  cassandraRepo.cassandraRepo

  def visitorEmail(visitor: Visitor): String = visitor match {
    case anon: Anonymous => "N/A"
    case user: User => user.email
  }

  println(visitorEmail(Anonymous("id-1")))
  println(visitorEmail(User("id-4", "ivan@mail.ru")))

  def factorial(n: Int): Int = {
    if (n == 1 || n == 0) 1     // base case
    else n * factorial(n - 1)   // recursive call
  }

  println(factorial(10))

  val example: Example = new Example {} // anonymous class

  val user2: Visitor = User("id-100", "mail@mail.ru")

  user2 match {
    case user: User => user.email
    case anon: Anonymous => anon.id
  }


  val list1 = Pair(2, Pair(10, Pair(5, Pair(-8, Pair(1, End)))))
  println(list1)

  def sumList(list: IntList): Int = list match {
    case End => 0
    case Pair(head, tail) => head + sumList(tail)
  }

  def sumListTailRecursive(list: IntList): Int = {
    def sum(list: IntList, acc: Int): Int = list match {
      case End => acc
      case Pair(head, tail) => sum(tail, acc + head)
    }
    sum(list, 0)
  }

  println(s"Sum of list is: ${sumList(list1)}")

}
