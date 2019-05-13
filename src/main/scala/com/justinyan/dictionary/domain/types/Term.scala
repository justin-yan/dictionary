package com.justinyan.dictionary.domain.types

class Term(canonicalForm: String, displayForm: String)

object Term {
  def apply(term: String) = new Term(term.toLowerCase.trim, term)
}
