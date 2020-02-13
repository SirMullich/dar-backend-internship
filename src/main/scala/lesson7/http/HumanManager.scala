package lesson7.http

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import model.{FailedResponse, HttpResponse, Human, HumanCreated, SuccessfulResponse}

//sealed trait HumanManagerCommand
//case class CreateHuman(human: Human, replyTo: ActorRef[String]) extends HumanManagerCommand

//object HumanManager {
//  def apply(): Behavior[HumanManagerCommand] = humanManager(Map())
//  var counter = 0
//
//  private def humanManager(humans: Map[String, Human]): Behavior[HumanManagerCommand] =
//    Behaviors.setup { context =>
//      Behaviors.receiveMessage {
//        case CreateHuman(human) =>
//          context.log.info("A new human received: {}", human)
//
//      }
//  }
//}

case class HumanManager() {

  var humans: Map[Int, Human] = Map()
  var counter = 0

  def createHuman(human: Human): HumanCreated = {
    humans = humans + (counter -> human)

    val response = HumanCreated(counter)
    counter += 1
    response
  }

  def getHuman(id: Int): Either[FailedResponse, Human] = {
    humans.get(id) match {
      case Some(human) => Right(human)
      case None => Left(FailedResponse("No such human exists, he-he"))
    }
  }


}