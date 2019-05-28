package com.justinyan.dictionary.infrastructure

import akka.event.Logging.LogLevel
import akka.event.LoggingAdapter
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult.Complete
import akka.http.scaladsl.server.directives.{DebuggingDirectives, LogEntry, LoggingMagnet}
import akka.stream.Materializer

import scala.concurrent.ExecutionContext

object Observability {
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
