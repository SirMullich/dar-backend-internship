package lesson8.repository

sealed trait OperationResult
case class SuccessfulOperation(msg: String) extends OperationResult
case class FailedOperation(msg: String) extends OperationResult