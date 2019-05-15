package com.justinyan.dictionary.domain

import com.justinyan.dictionary.domain.types._
import com.justinyan.dictionary.ports.dictionaryrepository.DictionaryRepository

import scala.util.Try

class DictionarySystem(dictRepo: DictionaryRepository) {

  /** BLOCK: Application User Use Cases
    * These methods enable people to define and look-up their initialisms and terms
    */
  def setExpansion(context: String, initialism: Initialism, term: Term): Try[Expansion] = {
    dictRepo.insertInitialism(initialism)
    dictRepo.insertTerm(term)
    dictRepo.addExpansion(context, initialism, term)
  }
  def setDefinition(context: String, term: Term, exposition: Exposition): Try[Definition] = {
    dictRepo.insertTerm(term)
    dictRepo.insertExposition(exposition)
    dictRepo.insertDefinition(context, term, exposition)
  }
  def setAll(context: String, initialism: Initialism, term: Term, exposition: Exposition) = {
    // TODO: This should flatmap the Try and return the composite structure
    setExpansion(context, initialism, term)
    setDefinition(context, term, exposition)
  }

  def lookupInitialism(context: String, initialism: Initialism): Try[List[Expansion]] = {
    // TODO: This should just always return the rich expansion if available
    dictRepo.getExpansion(context, initialism)
  }

  def lookupTerm(context: String, term: Term): Try[List[Definition]] = {
    dictRepo.getDefinition(context, term)
  }

  /** BLOCK: Admin Use Cases
    * These methods enable admins to curate the quality of a dictionary
    */
  def deleteInitialism() = ???
  def deleteTerm() = ???
  def deleteExposition() = ???
  def deleteExpansion() = ???
  def deleteDefinition() = ???

}
