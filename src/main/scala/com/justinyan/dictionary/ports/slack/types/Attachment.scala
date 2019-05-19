package com.justinyan.dictionary.ports.slack.types

import com.justinyan.dictionary.domain.types.Definition

case class Attachment(text: String, title: String, color: String = "#42D3AE", mrkdwn_in: List[String] = List("text"))
object Attachment {
  def fromDefinition(defn: Definition): Attachment = Attachment(
    s"${defn.exposition.exposition}${if (defn.synonyms.nonEmpty) "\n Synonyms: " + defn.synonyms.map(_.displayForm).mkString(",") else ""}", defn.term.displayForm
  )
}

