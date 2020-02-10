package lesson6

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object Bot {

  def apply(max: Int): Behavior[HelloWorld.Greeted] = bot(0, max)

  def bot(greetingCounter: Int, max: Int): Behavior[HelloWorld.Greeted] = Behaviors.receive { (context, message) =>
    val n = greetingCounter + 1
    context.log.info("Greeting {} for {}. I'm: {}", n, message.whom, context.self.path.name)
    if (n == max) Behaviors.stopped
    else {
      message.from ! HelloWorld.Greet(message.whom, context.self)
      val anotherActor = context.spawn(HelloWorldMain(), "another-actor")
      bot(n, max) // Behavior[HelloWorld.Greeted]
    }
  }
}
