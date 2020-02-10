package lesson6

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import scala.util.Random

object HelloWorldMain {

  case class SayHello(name: String)

  def apply(): Behavior[SayHello] = Behaviors.setup { context =>
    val greeter = context.spawn(HelloWorld(), "greeter")
    var counter = 0

    Behaviors.receiveMessage { message =>
      val replyTo = context.spawn(Bot(max = 2), s"${message.name}-$counter") // bot
      counter += 1
      greeter ! HelloWorld.Greet(message.name, replyTo)       // replyTo: bot
      Behaviors.same
    }
  }
}
