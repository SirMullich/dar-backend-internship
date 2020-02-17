package lesson8.repository

import lesson8.model.Job

import scala.concurrent.Future

trait Repository {
  def addJob(job: Job): Future[OperationResult]

  def getJob(id: Long): Future[Option[Job]]
}
