package lesson7.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.{ActorMaterializer, Materializer}
import model.FailedResponse
// for JSON serialization/deserialization following dependency is required:
// "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.7"
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import model.Human

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object Boot extends App with JsonSerializer {

  implicit val system: ActorSystem = ActorSystem("http-server")
  implicit val materializer: Materializer = Materializer(system) // materializes stream
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher


  val humanManager = HumanManager()

  // TODO: 1) PUT humans/<id>   entity Human
  // TODO: 2) DELETE humans/<id>
  // TODO: consider all the cases and errors
  // TODO: postman

  val route: Route =
    concat(
      // curl --location --request GET 'localhost:8080/healthcheck'
      path("healthcheck") {
        get {
          complete {
            "OK"
          }
        }
      },
      pathPrefix("humans") {
        concat(
//          curl --location --request POST 'localhost:8080/humans' \
//        --header 'Content-Type: application/json' \
//        --data-raw '{
//          "name": "Rob",
//          "age": 30,
//          "sex": "male"
//        }'
          post {
            entity(as[Human]) { human =>
              complete {
                humanManager.createHuman(human)
              }
            }
          },
          path(Segment) { humanId =>
            //curl --location --request GET 'localhost:8080/humans/2'
            get {
              complete {
                lesson6.Boot.readInt(humanId) match {
                  case Some(id) =>
                    humanManager.getHuman(id)
                  case None =>
                    FailedResponse("wrong id format")
                }
              }
            }
          }
        )
      }
    )


  Http().bindAndHandle(route, "0.0.0.0", 8080)

}
