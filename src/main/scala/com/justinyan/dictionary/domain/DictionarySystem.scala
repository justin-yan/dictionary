package com.justinyan.dictionary.domain

import com.justinyan.dictionary.domain.types._
import com.justinyan.dictionary.ports.dictionaryrepository.DictionaryRepository

import scala.util.Try

class DictionarySystem(dictRepo: DictionaryRepository) {
  type Context = String

  /** BLOCK: Application User Use Cases
    * These methods enable people to define and look-up their initialisms and terms
    */
  def defineTerm(term: Term, exposition: Exposition, context: Context): Try[Definition] = {
    dictRepo.insertDefinition(term, exposition, context)
  }

  def lookupTerm(term: Term): Try[List[Definition]] = {
    dictRepo.getAllDefinitions(term)
  }

  def lookupTerm(term: Term, context: Context): Try[Definition] = {
    dictRepo.getDefinition(term, context)
  }

  /** BLOCK: Admin Use Cases
    * These methods enable admins to curate the quality of a dictionary
    */
  def deleteTerm() = ???
  def deleteExposition() = ???
  def deleteDefinition() = ???

}
