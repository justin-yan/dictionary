package com.justinyan.dictionary.ports

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import com.justinyan.dictionary.domain.DictionarySystem
import com.justinyan.dictionary.domain.types._
import com.justinyan.dictionary.ports.slack.types.{Attachment, SlashResponse}
import spray.json._

import scala.util.{Failure, Success, Try}

package object slack extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val attachmentFormat = jsonFormat4(Attachment)
  implicit val itemFormat = jsonFormat3(SlashResponse.apply)

  def route(dictionarySystem: DictionarySystem) = {
    complete("YES")
  }

//  def route(dictionarySystem: DictionarySystem) = {
//    pathPrefix("slack") {
//      (path("v0" / "slash") & post & formFields('token, 'command, 'text)) { (token, command, text) =>
//        // TODO: Authorize token!
//        complete {
//          val cmd = Try(command match {
//            case "define" => parseDefinition(text)
//            case "lookup" => parseLookup(text)
//            case _ => throw new IllegalArgumentException("Invalid command")
//          })
//          cmd.flatMap(c => dictionarySystem.handleUserCommands(c)) match {
//            case Success(value) => SlashResponse.fromCommandExec(value)
//            case Failure(exception) => SlashResponse(exception.getMessage)
//          }
//        }
//      }
//    }
//  }
//
//  /**
//    * /define INI = term
//    * /define (context) INI = term
//    * /define (context) term = exposition
//    * /define (context) INI = "term" exposition
//    */
//  // TODO: throw exception on failed parsing to surface to end user
//  def parseDefinition(text: String): Define = {
//    val contextRE = "\\((.*)\\)(.*)".r
//    text match {
//      case contextRE(context, rest) =>
//        val defcmd = text.split("=", 2)
//        Initialism.from(defcmd.head) match {
//          case Success(i) => DefineInitialism(context, i, Term.from(defcmd.last))
//          case Failure(e) => DefineTerm(context, Term.from(defcmd.head), Exposition(defcmd.last))
//        }
//      case nonContextCmd =>
//        val defcmd = nonContextCmd.split("=", 2)
//        Initialism.from(defcmd.head) match {
//          case Success(i) => DefineInitialism(null, i, Term.from(defcmd.last))
//          case Failure(e) => DefineTerm(null, Term.from(defcmd.head), Exposition(defcmd.last))
//        }
//    }
//  }
//
//  // TODO: throw exception on failed parsing to surface to end user
//  def parseLookup(text: String): Lookup = {
//    val contextRE = "\\((.*)\\)(.*)".r
//    text match {
//      case contextRE(context, rest) =>
//        Initialism.from(rest) match {
//          case Success(i) => LookupInitialism(context, i)
//          case Failure(e) => LookupTerm(context, Term.from(rest))
//        }
//      case nonContextCmd =>
//        Initialism.from(nonContextCmd) match {
//          case Success(i) => LookupInitialism(null, i)
//          case Failure(e) => LookupTerm(null, Term.from(nonContextCmd))
//        }
//    }
//  }

}