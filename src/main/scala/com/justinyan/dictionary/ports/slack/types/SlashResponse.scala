package com.justinyan.dictionary.ports.slack.types

import com.justinyan.dictionary.domain.types.{Definition, Expansion}

case class SlashResponse(text: String, attachments: List[Attachment] = List(), response_type: String = "ephemeral")
object SlashResponse {
  def fromCommandExec(raw: AnyRef): SlashResponse = {
    raw match {
      case Expansion(i, t, s, c) => SlashResponse("", List(Attachment(t.displayForm, i.canonicalForm)))
      case Definition(t, e, s, c) => SlashResponse("", List(Attachment(e.exposition, t.displayForm)))
      case _ => unknownErrorResponse()
    }
  }
  def unknownErrorResponse() = SlashResponse("Unknown Error, Please Try Again!")
}
