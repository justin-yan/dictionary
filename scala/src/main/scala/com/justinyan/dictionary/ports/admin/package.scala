package com.justinyan.dictionary.ports

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import com.justinyan.dictionary.domain.DictionarySystem
import spray.json._

package object admin extends SprayJsonSupport with DefaultJsonProtocol {

  def route(dictionarySystem: DictionarySystem) = {
    pathPrefix("admin") {
      pathPrefix("v0") {
        path("lookup") {
          get { complete("blah")}
        }
      }
    }
  }
}
