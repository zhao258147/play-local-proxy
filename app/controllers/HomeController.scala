package controllers

import javax.inject._
import actors._
import akka.NotUsed
import akka.actor._
import akka.pattern.ask
import akka.stream.scaladsl._
import akka.util.Timeout
import play.api.Logger
import play.api.libs.json._
import play.api.libs.ws.WSClient
import play.api.mvc._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

/**
 * This class creates the actions and the websocket needed.
 */
@Singleton
class HomeController @Inject()(@Named("userParentActor") userParentActor: ActorRef,
                               cc: ControllerComponents, webservice: WSClient)
                              (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  val logger = play.api.Logger(getClass)

  // Home page that renders template
  def proxy = Action.async { request: Request[AnyContent] =>
    val urlOpt = request.getQueryString("url")
    println(urlOpt)
    urlOpt match {
      case Some(url) =>
        webservice.url(url).get.map {
          response =>
            val text = response.body.replaceAllLiterally("/wiki/", "http://localhost:9000/wikiproxy?url=https://en.wikipedia.org/wiki/")

            Ok(views.html.proxy(text))
        }
      //        Ok(webservice.url(url).get.map(_.body))
      case _ =>
        Future.successful(Ok("s"))
    }
  }

  def index = Action { implicit request: Request[AnyContent] =>

    Ok(views.html.index())
  }

}