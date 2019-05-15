package com.justinyan.dictionary.domain

import com.justinyan.dictionary.domain.types.{Exposition, Initialism, Term}
import com.justinyan.dictionary.ports.dictionaryrepository.InMemDictionaryRepository
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class DictionarySystemSpec extends FlatSpec with Matchers with BeforeAndAfter {

  var dictionarySystem: DictionarySystem = _

  before {
    dictionarySystem = new DictionarySystem(new InMemDictionaryRepository())
    dictionarySystem.setDefinition(null, Term.from("test term"), Exposition("This is a test term"))
    dictionarySystem.setExpansion(null, Initialism.from("TT").get, Term.from("test term"))
    dictionarySystem.setExpansion("flying", Initialism.from("TT").get, Term.from("turboprop training"))
    dictionarySystem.setExpansion("flying", Initialism.from("TT").get, Term.from("Training you do to fly a turboprop plane"))
    dictionarySystem.setExpansion(null, Initialism.from("TTT").get, Term.from("truly test term"))
    dictionarySystem.setDefinition(null, Term.from("truly test term"), Exposition("This is truly a test term"))
    dictionarySystem.setExpansion(null, Initialism.from("TTTT").get, Term.from("truly truly test term"))
  }

  // TERMs
  "A DictionarySystem" should "LOOKUP TERM on a SUCCESS DEFINE" in {
    // assert one without context
    var deflist = dictionarySystem.lookupTerm(null, Term.from("test term")).get
    assert(deflist.length == 1)
    assert(deflist.head.exposition.exposition == "This is a test term")
    // assert one with context
    // assert none without context
    // assert none with context
  }

  it should "OVERWRITE DEFINE if ALREADY DEFINED" in {
    // assert one without context
    // assert one with context
    // ?? assert old exposition remains
  }

  it should "LOOKUP TERM with MULTIPLE DEFINITIONS" in {
    // assert one nocontext + context
    // assert one twocontext
  }

  it should "LOOKUP TERM with CONTEXT FILTER" in {
    // assert one nocontext + context
    // assert one twocontext
    // assert none twocontext
  }

  // INIs
  it should "LOOKUP INITIALISM on a SUCCESS DEFINE EXPANSION" in {
    // assert one without context
    // assert one with context
    // assert none without context
    // assert none with context
  }

  it should "OVERWRITE EXPANSION if ALREADY DEFINED" in {
    // assert one without context
    // assert one with context
    // ?? assert old term remains
  }

  it should "LOOKUP INITIALISM with MULTIPLE EXPANSIONS" in {
    // assert one nocontext + context
    // assert one twocontext
  }

  it should "LOOKUP INITIALISM with CONTEXT FILTER" in {
    // assert one nocontext + context
    // assert one twocontext
    // assert none twocontext
  }

  // Rich Structs
  it should "RICH LOOKUP INITIALISM on a SUCCESS DEFINE ALL" in {
    // assert one expansion+definition without context
  }

  it should "RICH LOOKUP INITIALISM with MULTIPLE EXPANSIONS" in {
    // assert multiple expansions and multiple defs
  }

  it should "RICH LOOKUP INITIALISM with CONTEXT FILTER" in {
    // assert one nocontext + context
  }

}


