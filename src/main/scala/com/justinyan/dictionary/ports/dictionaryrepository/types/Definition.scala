package com.justinyan.dictionary.ports.dictionaryrepository.types

import slick.jdbc.H2Profile.api._

case class Definition(context: String, termId: Long, expoId: Long)

final class DefinitionTable(tag: Tag) extends Table[Definition](tag, "term") {

  def termId = column[Long]("TERM_ID")
  def expoId = column[Long]("EXPOSITION_ID")
  def context = column[String]("CONTEXT")
  def * = (context, termId, expoId).mapTo[Definition]

  def PK = primaryKey("primaryKey", (termId, expoId))
}
