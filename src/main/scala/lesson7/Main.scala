package lesson7

import akka.NotUsed
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Terminated}
import akka.actor.typed.scaladsl.Behaviors

object Main {
  def apply(): Behavior[NotUsed] =
    Behaviors.setup { context =>  // contructor
      val chatRoom = context.spawn(ChatRoom(), "chatroom")
      val gabblerRef: ActorRef[SessionEvent] = context.spawn(Gabbler(), "gabbler")

      context.watch(gabblerRef)

      val gabbler2 = context.spawn(Gabbler(), "gabbler2")
      val arteezy = context.spawn(Gabbler(), "arteezy")

      chatRoom ! GetSession("ol’ Gabbler", gabblerRef)
      chatRoom ! GetSession("Gabbler", gabbler2)
      chatRoom ! GetSession("Arteezy is da best", arteezy)

      Behaviors.receiveSignal {
        case (_, Terminated(_)) =>
          context.log.info("Oh no, Gabbler died!")
          Behaviors.stopped
      }
    }

  def main(args: Array[String]): Unit = {
    ActorSystem(Main(), "ChatRoomDemo")
  }
}
