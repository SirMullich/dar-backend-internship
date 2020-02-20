package lesson8.repository

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.Table
import lesson8.model.{Job, Status}

import scala.concurrent.Future

abstract class JobsTable extends Table[JobsTable, Job]{
  // Database representation
  object id extends LongColumn with PartitionKey
  object projectName extends StringColumn
  object status extends StringColumn
  object duration extends LongColumn

  def create(job: Job): Future[ResultSet] =
    insert
      .value(_.id, job.id)
      .value(_.projectName, job.projectName)
      .value(_.status, job.status)
      .value(_.duration, job.duration)
      .ifNotExists() // think about this
      .future()

  def read(id: Long): Future[Option[Job]] = {
    select.where(_.id.eqs(id)).one()
  } //select.where(_.id.eqs(id)).one()
}