package com.justinyan.dictionary.ports.dictionaryrepository

import com.justinyan.dictionary.domain.types.{Exposition, Term}
import org.scalatest._

class DictionaryRepositorySpec extends FlatSpec with Matchers {

  "An ERepo" should "look up exact definitions" in {
    val erepo = new InMemDictionaryRepository()
    erepo.insertDefinition(Term.from("blah"), Exposition.from("me"))
    erepo.getDefinition(Term.from("blah")).get.exposition.exposition should be ("me")
  }
}


