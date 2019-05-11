package com.justinyan.dictionary

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.justinyan.dictionary.domain.DictionarySystem
import com.justinyan.dictionary.ports.entryrepository.SetEntryRepository
import com.justinyan.dictionary.ports.slack.SlackRoutes

import scala.io.StdIn

object DictionaryApp extends App {
  val dictionarySystem = new DictionarySystem(new SetEntryRepository())

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val route = SlackRoutes.route(dictionarySystem)
  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}
