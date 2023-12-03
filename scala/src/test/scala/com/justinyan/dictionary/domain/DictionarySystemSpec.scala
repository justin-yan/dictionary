package com.justinyan.dictionary.domain

import com.justinyan.dictionary.domain.types.{Exposition, Term}
import com.justinyan.dictionary.ports.dictionaryrepository.InMemDictionaryRepository
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class DictionarySystemSpec extends FlatSpec with Matchers with BeforeAndAfter {

  var dictionarySystem: DictionarySystem = _

  before {
    dictionarySystem = new DictionarySystem(new InMemDictionaryRepository())
    dictionarySystem.defineTerm(Term.from("test term"), Exposition.from("This is a test term"), null)
    dictionarySystem.defineTerm(Term.from("truly test term"), Exposition.from("This is truly a test term"), null)
  }

  // TERMs
  "A DictionarySystem" should "LOOKUP TERM on a SUCCESS DEFINE" in {
  }

}


