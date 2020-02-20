package lesson8

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{Behavior, PostStop}
import akka.actor.typed.scaladsl.adapter._
import com.outworkers.phantom.dsl._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.Http
import lesson8.repository.{CassandraRepository, InMemoryRepository}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object Server {

  sealed trait Message
  private final case class StartFailed(cause: Throwable) extends Message
  private final case class Started(binding: ServerBinding) extends Message
  case object Stop extends Message

  def apply(host: String, port: Int): Behavior[Message] = Behaviors.setup { ctx =>

    implicit val system = ctx.system
    // http doesn't know about akka typed so provide untyped system
    implicit val untypedSystem: akka.actor.ActorSystem = ctx.system.toClassic
    // implicit materializer only required in Akka 2.5
    // in 2.6 having an implicit classic or typed ActorSystem in scope is enough
    implicit val materializer: ActorMaterializer = ActorMaterializer()(ctx.system.toClassic)
//    implicit val ec: ExecutionContextExecutor = ctx.system.executionContext

    val inMemoryRepository = InMemoryRepository()


    val cassandraRepository = CassandraRepository()
    // create tables
    cassandraRepository.database.create()


//    val application = ctx.spawn(Application(inMemoryRepository), "application") // in memory
    val application = ctx.spawn(Application(cassandraRepository), "application") // cassandra
    val routes = new JobRoutes(application)

    val serverBinding: Future[Http.ServerBinding] =
      Http.apply().bindAndHandle(routes.theJobRoutes, host, port)

    ctx.pipeToSelf(serverBinding) {
      case Success(binding) => Started(binding)
      case Failure(ex)      => StartFailed(ex)
    }

    def running(binding: ServerBinding): Behavior[Message] =
      Behaviors.receiveMessagePartial[Message] {
        case Stop =>
          ctx.log.info(
            "Stopping server http://{}:{}/",
            binding.localAddress.getHostString,
            binding.localAddress.getPort)
          Behaviors.stopped
      }.receiveSignal {
        case (_, PostStop) =>
          binding.unbind()
          Behaviors.same
      }

    def starting(wasStopped: Boolean): Behaviors.Receive[Message] =
      Behaviors.receiveMessage[Message] {
        case StartFailed(cause) =>
          throw new RuntimeException("Server failed to start", cause)
        case Started(binding) =>
          ctx.log.info(
            "Server online at http://{}:{}/",
            binding.localAddress.getHostString,
            binding.localAddress.getPort)
          if (wasStopped) ctx.self ! Stop
          running(binding)
        case Stop =>
          // we got a stop message but haven't completed starting yet,
          // we cannot stop until starting has completed
          starting(wasStopped = true)
      }

    starting(wasStopped = false)
  }
}