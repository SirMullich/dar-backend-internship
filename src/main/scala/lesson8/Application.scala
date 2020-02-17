package lesson8

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import lesson8.repository.{FailedOperation, Repository}

import scala.util.{Failure, Success}

object Application {
  // This behavior handles all possible incoming messages and keeps the state in the function parameter
  def apply(repository: Repository): Behavior[Command] = Behaviors.setup { ctx =>
    import ctx.executionContext

    Behaviors.receiveMessage {
      case AddJob(job, replyTo) =>
        repository.addJob(job).onComplete {
          case Success(value) =>
            replyTo ! value
          case Failure(exception) =>
            replyTo ! FailedOperation(exception.getMessage)
        }
        Behaviors.same

      case GetJobById(id, replyTo) =>
        repository.getJob(id).foreach { job =>
          replyTo ! job
        }
//        repository.getJob(id).onComplete {
//          case Success(value) =>
//            replyTo ! value
//          case Failure(exception) =>
//            replyTo ! None
//        }
        Behaviors.same
//      case ClearJobs(replyTo) =>
//        replyTo ! OK
//        JobRepository(Map.empty)
    }
  }
}
