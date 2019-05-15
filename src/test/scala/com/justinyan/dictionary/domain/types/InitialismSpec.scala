package com.justinyan.dictionary.domain.types

import org.scalatest.{FlatSpec, Matchers}

class InitialismSpec extends FlatSpec with Matchers {

  "An Initialism" should "fail when constructed with not all upper-case form" in {
    assert(false.equals(Initialism.from("blah").getOrElse(false)))
    assert(false.equals(Initialism.from("fa3opDFO").getOrElse(false)))
  }

  it should "fail when constructed with whitespace" in {
    assert(false.equals(Initialism.from("bl ah").getOrElse(false)))
    assert(false.equals(Initialism.from("fa 3opDFO").getOrElse(false)))
  }

}


