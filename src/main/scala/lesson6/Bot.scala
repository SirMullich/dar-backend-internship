package lesson6

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps



object Bot {

  def apply(max: Int): Behavior[HelloWorld.Greeted] = bot(0, max)

  def bot(greetingCounter: Int, max: Int): Behavior[HelloWorld.Greeted] = Behaviors.receive { (context, message) =>
    val n = greetingCounter + 1
    context.log.info(s"Greeting ${n} for ${message.whom}. I'm: ${context.self.path.name}")
    if (n == max) Behaviors.stopped
    else {
      message.from ! HelloWorld.Greet(message.whom, context.self)
      val anotherActor = context.spawn(HelloWorldMain(), "another-actor")
      bot(n, max) // Behavior[HelloWorld.Greeted]
    }
  }
}
