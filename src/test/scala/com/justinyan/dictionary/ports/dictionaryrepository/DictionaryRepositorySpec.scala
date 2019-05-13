package com.justinyan.dictionary.ports.dictionaryrepository

import com.justinyan.dictionary.domain.types.Definition
import org.scalatest._

class DictionaryRepositorySpec extends FlatSpec with Matchers {

  "An ERepo" should "look up exact definitions" in {
    val erepo = new InMemDictionaryRepository()
    erepo.createDefinition(Definition())
    erepo.getDefinition(Definition).get.definition should be ("me")
  }
}


