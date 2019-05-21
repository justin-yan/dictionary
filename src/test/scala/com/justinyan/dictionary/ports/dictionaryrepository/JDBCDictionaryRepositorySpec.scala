package com.justinyan.dictionary.ports.dictionaryrepository

import slick.jdbc.H2Profile.api._

import com.justinyan.dictionary.domain.types.{Exposition, Term}
import org.scalatest._

class JDBCDictionaryRepositorySpec extends FlatSpec with Matchers with BeforeAndAfter {

  var dictionaryRepo: JDBCDictionaryRepository = _
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
    val db = Database.forConfig("h2db")
    dictionaryRepo = new JDBCDictionaryRepository(db)
    dictionaryRepo.bootstrapDatabase().get
  }

  "A JDBCDictionaryRepo" should "Do Other Things" in {
    // assert one for def with only null context
    assert(dictionaryRepo.getTerms("3").get.canonical == "test3")
  }

}
