package com.justinyan.dictionary.ports.dictionaryrepository.types

import slick.jdbc.H2Profile.api._

case class Exposition(exposition: String, id: Long = 0L)

final class ExpositionTable(tag: Tag) extends Table[Exposition](tag, "term") {

  def id = column[Long]("TERM_ID", O.PrimaryKey, O.AutoInc)
  def exposition = column[String]("EXPOSITION")
  def * = (exposition, id).mapTo[Exposition]
}
