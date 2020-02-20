package lesson8.repository

import com.outworkers.phantom.database.DatabaseProvider
import com.outworkers.phantom.dsl._
import lesson8.model.Job

import scala.concurrent.{ExecutionContext, Future}

case class CassandraRepository()(implicit executionContext: ExecutionContext) extends DatabaseProvider[AppDatabase]
  with Repository {
  override val database: AppDatabase = new AppDatabase(CassandraConnector.cassandraConnection)

  override def addJob(job: Job): Future[OperationResult] = {
    database.jobsTable.create(job).map { resultSet =>
      SuccessfulOperation("Job successfully added")
    }
  }

  override def getJob(id: Long): Future[Option[Job]] = database.jobsTable.read(id)
}