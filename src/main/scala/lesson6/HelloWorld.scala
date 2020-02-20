package lesson6

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps
import akka.actor.typed.{ ActorRef, ActorSystem, Behavior }

object HelloWorld {

  case class Greet(whom: String, replyTo: ActorRef[Greeted])
  case class Greeted(whom: String, from: ActorRef[Greet])

  // actor1 -> context.self  Greet
  // context.self -> actor1  Greeted

  def apply(): Behavior[Greet] = Behaviors.receive { (context, message) => // when msg is received
//    context.log.info("Hello {}. I'm: {}", message.whom, context.self.path.name) // log
    message.replyTo ! Greeted(message.whom, context.self) // reply to actor (bot)
    Behaviors.same                // do not change behavior
  }

  // Any => Un
}
