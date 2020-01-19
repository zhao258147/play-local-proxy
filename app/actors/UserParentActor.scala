package actors

import akka.actor._
import akka.event.LoggingReceive
import akka.util.Timeout
import javax.inject.Inject
import play.api.Configuration
import play.api.libs.concurrent.InjectedActorSupport

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
 * Provide some DI and configuration sugar for new UserActor instances.
 */
class UserParentActor @Inject()(configuration: Configuration)
                               (implicit ec: ExecutionContext)
  extends Actor with InjectedActorSupport with ActorLogging {

  import UserParentActor._

  implicit val timeout = Timeout(2.seconds)

  override def receive: Receive = LoggingReceive {
    case Create(id) =>
      println("actor example")
  }
}

object UserParentActor {
  case class Create(id: String)
}