package lesson7

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

sealed trait RoomCommand
final case class GetSession(screenName: String, replyTo: ActorRef[SessionEvent]) extends RoomCommand
final case class PublishSessionMessage(screenName: String, message: String) extends RoomCommand

sealed trait SessionEvent
final case class SessionGranted(handle: ActorRef[PostMessage]) extends SessionEvent
final case class SessionDenied(reason: String) extends SessionEvent
final case class MessagePosted(screenName: String, message: String) extends SessionEvent

sealed trait SessionCommand
final case class PostMessage(message: String) extends SessionCommand
final case class NotifyClient(message: MessagePosted) extends SessionCommand


object ChatRoom {

  def apply(): Behavior[RoomCommand] =
    chatRoom(List.empty)

  private def chatRoom(sessions: List[ActorRef[SessionCommand]]): Behavior[RoomCommand] =
    Behaviors.receive { (context, message) => message match {
        case GetSession(screenName, client) =>
          // behavior when SessionCommand is received
          val behavior: Behavior[SessionCommand] = session(context.self, screenName, client)

          if (screenName != "ol’ Gabbler") {
            // create a child actor for further interaction with the client
            // ses is an Actor
            val ses: ActorRef[SessionCommand] = context.spawn(behavior,
              name = URLEncoder.encode(screenName, StandardCharsets.UTF_8.name))
            client ! SessionGranted(ses) // reply client
            chatRoom(ses :: sessions)
          } else {
            client ! SessionDenied("I don't like you")
            Behaviors.same
          }

        case PublishSessionMessage(screenName, message) =>
          val notification = NotifyClient(MessagePosted(screenName, message))
          sessions.foreach(sessionActor => sessionActor ! notification)
          Behaviors.same
      }
    }

  private def session( room: ActorRef[PublishSessionMessage],
                       screenName: String,
                       client: ActorRef[SessionEvent]): Behavior[SessionCommand] =  // behavior
    Behaviors.receiveMessage {
      case PostMessage(message) =>
        // from client, publish to others via the room
        room ! PublishSessionMessage(screenName, message)
        Behaviors.same

      case NotifyClient(message) =>
        // published from the room
        client ! message
        Behaviors.same
    }
}





