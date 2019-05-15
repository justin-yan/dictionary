package com.justinyan.dictionary.domain.types

import org.scalatest.{FlatSpec, Matchers}

class TermSpec extends FlatSpec with Matchers {

  "A Term" should "have whitespace regularized in the constructor" in {
    assert("my words".equals(Term.from("my     words").canonicalForm))
    assert("theseare my words".equals(Term.from("theseare my    words   ").canonicalForm))
  }

}
