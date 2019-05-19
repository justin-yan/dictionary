package com.justinyan.dictionary.ports.slack.types

import com.justinyan.dictionary.domain.types.Definition

case class Attachment(text: String, title: String, color: String = "#42D3AE", mrkdwn_in: List[String] = List("text"))
object Attachment {
  def fromDefinition(defn: Definition): Attachment = Attachment(defn.exposition.exposition, defn.term.displayForm)
}

