package lesson11

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.duration._


sealed trait BotCommand
case object Tick extends BotCommand

object Bot {
  def apply(count: Int): Behavior[BotCommand] = Behaviors.withTimers[BotCommand] { timers =>
    timers.startTimerWithFixedDelay(Tick, 5.seconds)
    Behaviors.setup { ctx =>
      Behaviors.receiveMessage {
        case Tick =>
          ctx.log.info(s"Current count: ${count}")
          apply(count + 1)
      }
    }
  }
}
