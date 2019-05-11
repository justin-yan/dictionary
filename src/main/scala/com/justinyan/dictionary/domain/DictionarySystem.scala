package com.justinyan.dictionary.domain

import com.justinyan.dictionary.domain.types.Entry
import com.justinyan.dictionary.ports.entryrepository.EntryRepository

import scala.util.Try

class DictionarySystem(termRepository: EntryRepository) {
  def defineTerm(entry: Entry): Try[Entry] = termRepository.defineTerm(entry)
  def lookupTerm(term: String): Try[Entry] = termRepository.lookupTerm(term)
}
