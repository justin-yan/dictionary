package com.justinyan.dictionary.ports.dictionaryrepository

import com.justinyan.dictionary.domain.types._

import scala.util.Try

trait DictionaryRepository {
  type Context = String
  def insertDefinition(term: Term, exposition: Exposition, context: Context = null): Try[Definition]
  def getAllDefinitions(term: Term): Try[List[Definition]]
  def getDefinition(term: Term, context: Context = null): Try[Definition]
//  def updateDefinition(context: String, term: Term, exposition: Exposition): Try[Definition]
//  def deleteDefinition(context: String, term: Term): Try[Definition]

}
