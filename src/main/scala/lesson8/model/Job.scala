package lesson8.model

sealed trait Status {
  def strValue: String = this match {
    case Successful => "Successful"
    case Failed => "Failed"
  }
}
case object Successful extends Status
case object Failed extends Status

final case class Job(id: Long, projectName: String, status: String, duration: Long)
final case class Jobs(jobs: Seq[Job])
