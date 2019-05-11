package com.justinyan.dictionary.ports.entryrepository

import com.justinyan.dictionary.domain.types.Entry

import scala.collection.mutable
import scala.util.{Try, Success}


class SetEntryRepository extends EntryRepository {
  val storage = new mutable.HashMap[String, String]()

  def defineTerm(entry: Entry): Try[Entry] = {
    storage(entry.term) = entry.definition
    Success(entry)
  }

  def lookupTerm(term: String): Try[Entry] = {
    Try(storage.get(term).map(defn => Entry(term, defn)).get)
  }
}
