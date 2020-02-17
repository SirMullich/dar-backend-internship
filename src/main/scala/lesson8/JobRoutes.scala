package lesson8

import akka.actor.typed.{ActorRef, ActorSystem, Scheduler}
import akka.util.Timeout
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import lesson8.model.Job
import lesson8.repository.{FailedOperation, OperationResult, SuccessfulOperation}

import scala.concurrent.duration._
import scala.concurrent.Future

class JobRoutes(application: ActorRef[Command])(implicit system: ActorSystem[_]) extends JsonSupport {

  import akka.actor.typed.scaladsl.AskPattern._

  // asking someone requires a timeout and a scheduler, if the timeout hits without response
  // the ask is failed with a TimeoutException
  implicit val timeout: Timeout = 3.seconds

  // implicit scheduler only needed in 2.5
  // in 2.6 having an implicit typed ActorSystem in scope is enough
//  implicit val scheduler: Scheduler = system.scheduler

  lazy val theJobRoutes: Route =
    pathPrefix("jobs") {
      concat(
        pathEnd {
          concat(
            post {
              entity(as[Job]) { job =>
                val resp: Future[OperationResult] = application.ask(AddJob(job, _))

                onSuccess(resp) { // when future completes successfully
                  case res: SuccessfulOperation => complete(res)
                  case res: FailedOperation => complete(StatusCodes.InternalServerError -> res)
                }
              }
            }
            // TODO: implement get JOBS, return all jobs
//            ,
//            delete {
//              val operationPerformed: Future[JobRepository.Response] =
//                application.ask(JobRepository.ClearJobs(_))
//              onSuccess(operationPerformed) {
//                case JobRepository.OK         => complete("Jobs cleared")
//                case JobRepository.KO(reason) => complete(StatusCodes.InternalServerError -> reason)
//              }
//            }

            // TODO: implement PUT job (consider all cases)
          )
        },

        path(LongNumber) { jobId =>
          concat(
            get {
              val maybeJob: Future[Option[Job]] =
                application.ask(GetJobById(jobId, _))
              rejectEmptyResponse {
                complete(maybeJob)
              }
            }
            // TODO: implement DELETE
//            ,
//            delete {
//
//            }
          )
        }
      )
    }
}