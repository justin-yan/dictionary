package com.justinyan.dictionary.domain.types

case class Term(canonicalForm: String, displayForm: String)

object Term {
  def from(rawTerm: String): Term = {
    val trimmedTerm = rawTerm.trim
    Term(trimmedTerm.split("\\s+").mkString(" ").toLowerCase, trimmedTerm)
  }
}
