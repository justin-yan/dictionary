package com.justinyan.dictionary.ports.slack.types

case class SlashResponse(text: String, attachments: List[Attachment] = List(), response_type: String = "ephemeral")
