package com.justinyan.dictionary.ports.entryrepository

import com.justinyan.dictionary.domain.types.Entry

import scala.util.Try

trait EntryRepository {
  def defineTerm(entry: Entry): Try[Entry]
  def lookupTerm(term: String): Try[Entry]
}
