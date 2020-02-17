package lesson8

import akka.actor.typed.ActorRef
import lesson8.model.{Job, Status}
import lesson8.repository.OperationResult

// Trait defining successful and failure responses
sealed trait Response
case object OK extends Response
final case class KO(reason: String) extends Response

// Trait and its implementations representing all possible messages that can be sent to this Behavior
sealed trait Command
final case class AddJob(job: Job, replyTo: ActorRef[OperationResult]) extends Command
final case class GetJobById(id: Long, replyTo: ActorRef[Option[Job]]) extends Command
final case class GetJobByStatus(status: Status, replyTo: ActorRef[Seq[Job]]) extends Command
final case class ClearJobs(replyTo: ActorRef[Response]) extends Command