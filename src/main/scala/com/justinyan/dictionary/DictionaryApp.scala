package com.justinyan.dictionary

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.event.Logging.LogLevel
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult.Complete
import akka.http.scaladsl.server.directives.{DebuggingDirectives, LogEntry, LoggingMagnet}
import akka.stream.{ActorMaterializer, Materializer}
import com.justinyan.dictionary.domain.DictionarySystem
import com.justinyan.dictionary.ports.admin
import com.justinyan.dictionary.ports.dictionaryrepository.JDBCDictionaryRepository
import com.justinyan.dictionary.ports.slack

import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.H2Profile.api._

object DictionaryApp extends App {
  implicit val system = ActorSystem("dictionary-server")
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val config = DictionaryConfig.load()

  // Wiring up object references instead of creating a more complex actor topology
  val db = Database.forConfig("h2db")
  val entryRepository = new JDBCDictionaryRepository(db)
  val dictionarySystem = new DictionarySystem(entryRepository)
  val slackPort = slack.route(dictionarySystem)
  val adminPort = admin.route(dictionarySystem)

  val akkaRoutes = slackPort ~ adminPort ~ (get & pathPrefix("health")) { complete("OK") }
  val loggedAkkaRoutes = requestLoggingWrapper(Logging.InfoLevel, akkaRoutes)
  val bindingFuture = Http().bindAndHandle(loggedAkkaRoutes, config.httphost, config.httpport)
  // SHUTDOWN: We're expecting connection draining to take place at the service orchestration layer with blue-green deploys, so no special logic here.

  def requestLoggingWrapper(level: LogLevel, route: Route)(implicit m: Materializer, ex: ExecutionContext) = {
    def myLogger(logger: LoggingAdapter)(req: HttpRequest)(res: Any): Unit = {
      val entry = res match {
        case Complete(resp) => LogEntry(s"${req.method} ${req.uri}: ${resp.status} \n entity: ${resp.entity.toString}", level)
        case other => LogEntry(s"$other", level)
      }
      entry.logTo(logger)
    }
    DebuggingDirectives.logRequestResult(LoggingMagnet(log => myLogger(log)))(route)
  }
}
