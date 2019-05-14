package com.justinyan.dictionary.domain.types

case class Term(canonicalForm: String, displayForm: String)

object Term {
  def from(rawTerm: String): Term = Term(rawTerm.split("\\s+").mkString(" ").toLowerCase.trim, rawTerm)
}
