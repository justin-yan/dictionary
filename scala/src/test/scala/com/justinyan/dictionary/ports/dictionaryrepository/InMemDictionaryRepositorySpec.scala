package com.justinyan.dictionary.ports.dictionaryrepository

import com.justinyan.dictionary.domain.types.{Exposition, Term}
import org.scalatest._

class InMemDictionaryRepositorySpec extends FlatSpec with Matchers with BeforeAndAfter {

  var dictionaryRepo: DictionaryRepository = _
  val NONEXISTENT_TERM = "nonexistent"
  val TEST_TERM_1 = "test term"
  val TEST_EXPO_1 = "This is a test term"
  val TEST_TERM_2 = "truly test term"
  val TEST_EXPO_2 = "This is truly a test term"
  val TEST_TERM_3 = "synonym test term"
  val TEST_EXPO_3 = TEST_EXPO_2
  val TEST_CONTEXT_2 = "contest"
  val TEST_EXPO_2_CONTEXT_2 = "This is truly a test term with context"

  before {
    dictionaryRepo = new InMemDictionaryRepository()
    dictionaryRepo.insertDefinition(Term.from(TEST_TERM_1), Exposition.from(TEST_EXPO_1))
    dictionaryRepo.insertDefinition(Term.from(TEST_TERM_2), Exposition.from(TEST_EXPO_2))
    dictionaryRepo.insertDefinition(Term.from(TEST_TERM_2), Exposition.from(TEST_EXPO_2_CONTEXT_2), TEST_CONTEXT_2)
    dictionaryRepo.insertDefinition(Term.from(TEST_TERM_3), Exposition.from(TEST_EXPO_3))
  }

  // TERMs
  "An InMemDictionaryRepo" should "FIND INSERTED DEFS" in {
    // assert one for def with only null context
    var deflist = dictionaryRepo.getAllDefinitions(Term.from(TEST_TERM_1)).get
    assert(deflist.length == 1)
    assert(deflist.head.exposition.exposition == TEST_EXPO_1)
    // assert get def for null context
    assert(dictionaryRepo.getDefinition(Term.from(TEST_TERM_1)).get.exposition.exposition == TEST_EXPO_1)

    // assert two for get all defs with multiple contexts
    deflist = dictionaryRepo.getAllDefinitions(Term.from(TEST_TERM_2)).get
    assert(deflist.length == 2)
    // assert get def for context present
    assert(dictionaryRepo.getDefinition(Term.from(TEST_TERM_2), TEST_CONTEXT_2).get.exposition.exposition == TEST_EXPO_2_CONTEXT_2)
  }

  it should "NOT FIND NONEXISTENT DEFS" in {
    // assert none
    var deflist = dictionaryRepo.getAllDefinitions(Term.from(NONEXISTENT_TERM)).get
    assert(deflist.isEmpty)
    assert(dictionaryRepo.getDefinition(Term.from(NONEXISTENT_TERM)).isFailure)
  }

  it should "FIND SYNONYMS" in {
    var defn = dictionaryRepo.getDefinition(Term.from(TEST_TERM_3)).get
    assert(defn.synonyms.nonEmpty)
    defn = dictionaryRepo.getDefinition(Term.from(TEST_TERM_1)).get
    assert(defn.synonyms.isEmpty)
  }


}
