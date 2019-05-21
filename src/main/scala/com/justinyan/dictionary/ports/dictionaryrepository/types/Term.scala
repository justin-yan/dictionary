package com.justinyan.dictionary.ports.dictionaryrepository.types

import slick.jdbc.H2Profile.api._

case class Term(canonical: String, display: String, id: Long = 0L)

final class TermTable(tag: Tag) extends Table[Term](tag, "term") {

  def id = column[Long]("TERM_ID", O.PrimaryKey, O.AutoInc)
  def canonical = column[String]("CANONICAL")
  def display = column[String]("DISPLAY")
  def * = (canonical, display, id).mapTo[Term]
}
