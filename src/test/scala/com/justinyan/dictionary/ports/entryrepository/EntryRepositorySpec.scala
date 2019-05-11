package com.justinyan.dictionary.ports.entryrepository

import org.scalatest._
import com.justinyan.dictionary.domain.types.Entry

class EntryRepositorySpec extends FlatSpec with Matchers {

  "An ERepo" should "look up exact definitions" in {
    val erepo = new SetEntryRepository()
    erepo.defineTerm(Entry("test", "me"))
    erepo.lookupTerm("test").get.definition should be ("me")
  }
}


