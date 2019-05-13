package com.justinyan.dictionary.domain

import com.justinyan.dictionary.domain.types.{Definition, Expansion, Initialism, Term}
import com.justinyan.dictionary.ports.dictionaryrepository.DictionaryRepository

import scala.util.Try

class DictionarySystem(dictRepo: DictionaryRepository) {
  // interpret the source to determine if it's an initialism or not
  // then delegate to the correct method
  def define(source: String, target: String)
  def lookup(source: String)
  def delete(source: String)

  // determine whether source is or isn't an initialism
  private def matchSource(source: String)

  def setExpansion(initialism: String, term: String): Try[Expansion] = {
    val ini = Initialism(initialism)
    val term = Term(term)
    dictRepo.insertInitialism(ini)
    dictRepo.insertTerm(term)
    dictRepo.addExpansion(ini, term)
    // What happens if it already exists?

  }
  def expandInitialism(initialism: String): Try[Expansion] = {
    val ini = Initialism(initialism)
    dictRepo.getExpansion(ini)
  }

  def setDefinition(term: String, exposition: String): Try[Definition]
  def defineTerm(term: String): Try[Definition]

}
