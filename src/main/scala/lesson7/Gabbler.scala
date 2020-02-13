package lesson7

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object Gabbler {

//  import ChatRoom._

  def apply(): Behavior[SessionEvent] = Behaviors.setup { context =>
    val name = "I'm Gabbler"

    Behaviors.receiveMessage {
      case SessionGranted(handle) =>
        // handle is Session
        handle ! PostMessage("Hi all!")
        Behaviors.same
      case SessionDenied(reason) =>
        Behaviors.stopped


      case MessagePosted(screenName, message) =>
        context.log.info("Message has been posted by '{}': {}", screenName, message)
        Behaviors.same
    }

  }

}
