package com.justinyan.dictionary.ports

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import com.justinyan.dictionary.domain.DictionarySystem
import com.justinyan.dictionary.domain.types._
import com.justinyan.dictionary.ports.slack.types.{Attachment, SlashResponse}
import spray.json._

import scala.util.{Failure, Success, Try}

package object slack extends SprayJsonSupport with DefaultJsonProtocol {
  type Context = String
  implicit val attachmentFormat = jsonFormat4(Attachment.apply)
  implicit val itemFormat = jsonFormat3(SlashResponse.apply)
  val contextRE = "\\((.*?)\\)(.*)".r

  def route(dictionarySystem: DictionarySystem) = {
    pathPrefix("slack") {
      (path("v0" / "slash") & post & formFields('token, 'command, 'text)) { (token, command, text) =>
        // TODO: Authorize token!
        complete {
          command match {
            case "define" =>
              parseDefinition(text).flatMap(
                triple => dictionarySystem.defineTerm(triple._2, triple._3, triple._1)
              ) match {
                case Success(innerdef) => SlashResponse("New definition created", List(Attachment.fromDefinition(innerdef)))
                case Failure(e) => SlashResponse.fromException(e)
              }
            case "lookup" =>
              parseLookup(text) match {
                case (null, term) =>
                  dictionarySystem.lookupTerm(term) match {
                    case Success(deflist) => SlashResponse("", deflist.map(Attachment.fromDefinition))
                    case Failure(e) => SlashResponse.fromException(e)
                  }
                case (ctx, term) =>
                  dictionarySystem.lookupTerm(term, ctx) match {
                    case Success(defn) => SlashResponse("", List(Attachment.fromDefinition(defn)))
                    case Failure(e) => SlashResponse.fromException(e)
                  }
              }
            case _ => SlashResponse.fromException(new IllegalArgumentException("Invalid Command"))
          }
        }
      }
    }
  }

  /**
    * /define (context) term = exposition
    */
  def parseDefinition(text: String): Try[(Context, Term, Exposition)] = {
    val (context, rest) = text match {
      case contextRE(ctx, rst) => (ctx, rst)
      case nonContextCmd => (null, nonContextCmd)
    }
    Try(rest.split("=", 2) match {
      case Array(trm, exp) => (context, Term.from(trm), Exposition.from(exp))
      case _ => throw new IllegalArgumentException("Definition requires an '='.")
    })
  }

  /**
    * /lookup (context) term
    */
  def parseLookup(text: String): (Context, Term) = {
    text match {
      case contextRE(ctx, rst) => (ctx, Term.from(rst))
      case nonContextCmd => (null, Term.from(nonContextCmd))
    }
  }

}