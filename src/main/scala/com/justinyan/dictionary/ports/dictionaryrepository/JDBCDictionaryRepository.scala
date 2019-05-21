package com.justinyan.dictionary.ports.dictionaryrepository

import com.justinyan.dictionary.domain
import com.justinyan.dictionary.ports.dictionaryrepository.types.{DefinitionTable, ExpositionTable, Term, TermTable}

import scala.util.Try
import slick.jdbc.H2Profile.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class JDBCDictionaryRepository(database: Database) extends DictionaryRepository {
  val termQ = TableQuery[TermTable]
  val expoQ = TableQuery[ExpositionTable]
  val definitionQ = TableQuery[DefinitionTable]

  def bootstrapDatabase() = {
    Try(Await.result(database.run(termQ.schema.create), 10 seconds))
    Await.result(database.run(termQ ++= Seq(Term("test1", "1"), Term("test2", "2"), Term("test3", "3"), Term("test4", "4"))), 10 seconds)
  }

  def getTerms(term: String) = {
    println(Await.result(database.run(termQ.result), 10 seconds))

    Try(Await.result(database.run(termQ.filter(_.display === "3").result), 10 seconds)).map(_.headOption.get)
  }

  def insertDefinition(term: domain.types.Term, exposition: domain.types.Exposition, context: Context = null): Try[domain.types.Definition] = ???
  def getAllDefinitions(term: domain.types.Term): Try[List[domain.types.Definition]] = ???
  def getDefinition(term: domain.types.Term, context: Context = null): Try[domain.types.Definition] = ???
}
