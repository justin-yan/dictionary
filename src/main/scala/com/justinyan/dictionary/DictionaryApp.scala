package com.justinyan.dictionary

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.justinyan.dictionary.domain.DictionarySystem
import com.justinyan.dictionary.ports.admin
import com.justinyan.dictionary.ports.dictionaryrepository.InMemDictionaryRepository
import com.justinyan.dictionary.ports.slack

object DictionaryApp extends App {
  implicit val system = ActorSystem("dictionary-server")
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val config = DictionaryConfig.load()

  // Wiring up object references instead of creating a more complex actor topology
  val entryRepository = new InMemDictionaryRepository()
  val dictionarySystem = new DictionarySystem(entryRepository)
  val slackPort = slack.route(dictionarySystem)
  val adminPort = admin.route(dictionarySystem)

  val akkaRoutes = slackPort ~ adminPort ~ (get & pathPrefix("health")) { complete("OK") }
  val bindingFuture = Http().bindAndHandle(akkaRoutes, config.httphost, config.httpport)
  // SHUTDOWN: We're expecting connection draining to take place at the service orchestration layer with blue-green deploys, so no special logic here.
}
