package com.justinyan.dictionary.ports.slack.types

case class SlashResponse(text: String, attachments: List[Attachment] = List(), response_type: String = "ephemeral")

case class Attachment(text: String, title: String, color: String = "#42D3AE", mrkdwn_in: List[String] = List("text"))
