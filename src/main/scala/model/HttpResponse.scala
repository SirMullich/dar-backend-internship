package model

sealed trait HttpResponse
case class SuccessfulResponse(message: String) extends HttpResponse
case class HumanCreated(id: Int) extends HttpResponse
case class FailedResponse(message: String) extends HttpResponse
