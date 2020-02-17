package lesson8

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import lesson8.model._
import lesson8.repository.{FailedOperation, SuccessfulOperation}
import spray.json.DefaultJsonProtocol
import spray.json.DeserializationException
import spray.json.JsString
import spray.json.JsValue
import spray.json.RootJsonFormat

trait JsonSupport extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit object StatusFormat extends RootJsonFormat[Status] {
    def write(status: Status): JsValue = status match {
      case Failed     => JsString("Failed")
      case Successful => JsString("Successful")
    }

    def read(json: JsValue): Status = json match {
      case JsString("Failed")     => Failed
      case JsString("Successful") => Successful
      case _                      => throw new DeserializationException("Status unexpected")
    }
  }

  implicit val jobFormat = jsonFormat4(Job)

  implicit val jobsFormat = jsonFormat1(Jobs)

  implicit val operationSuccessFormat = jsonFormat1(SuccessfulOperation)
  implicit val failedOperationFormat = jsonFormat1(FailedOperation)
}
