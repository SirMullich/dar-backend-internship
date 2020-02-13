package lesson7.http

import model.{FailedResponse, Human, HumanCreated}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonSerializer extends DefaultJsonProtocol {

  implicit val humanCreatedFormat: RootJsonFormat[HumanCreated] = jsonFormat1(HumanCreated)
  implicit val humanFormat: RootJsonFormat[Human] = jsonFormat3(Human)
  implicit val failedResponse = jsonFormat1(FailedResponse)

}
