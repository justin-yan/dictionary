package com.justinyan.dictionary.ports.slack

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import com.justinyan.dictionary.domain.DictionarySystem
import com.justinyan.dictionary.domain.types.Entry
import com.justinyan.dictionary.ports.slack.types.{Attachment, SlashResponse}
import spray.json._

object SlackRoutes extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val attachmentFormat = jsonFormat4(Attachment)
  implicit val itemFormat = jsonFormat3(SlashResponse)

  def route(dictionarySystem: DictionarySystem) = {
    pathPrefix("slack") {
      pathPrefix("v0") {
        path("slash") {
          post {
            formFields('token, 'command, 'text) { (token, command, text) =>
              complete {
                command match {
                  case "define" =>
                    val defcmd = text.split("=", 2)
                    val resp: SlashResponse = dictionarySystem.defineTerm(Entry(defcmd.head, defcmd.last))
                      .map(entry => SlashResponse("", List(Attachment(entry.definition, entry.term))))
                      .getOrElse(SlashResponse("Please Try Again"))
                    resp
                  case "lookup" =>
                    val resp: SlashResponse = dictionarySystem.lookupTerm(text)
                      .map(entry => SlashResponse("", List(Attachment(entry.definition, entry.term))))
                      .getOrElse(SlashResponse("Please Try Again"))
                    resp
                  case _ => SlashResponse("Please Try Again")
                }
              }
            }
          }
        }
      }
    }
  }
}