package lesson8.repository
import lesson8.model.Job

import scala.concurrent.{ExecutionContext, Future}

case class InMemoryRepository()(implicit ex: ExecutionContext) extends Repository {

  var jobs: List[Job] = List.empty

  override def addJob(job: Job): Future[OperationResult] = Future {
    if (jobs.exists(_.id == job.id)) {
      FailedOperation("such job already exists")
    } else {
      jobs = jobs :+ job
      SuccessfulOperation("added")
    }
  }

  override def getJob(id: Long): Future[Option[Job]] = Future {
    jobs.find(_.id == id)
  }
}
