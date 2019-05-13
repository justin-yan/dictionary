package com.justinyan.dictionary.domain.types

case class Initialism(canonicalForm: String, displayForm: String)

object Initialism {
  def apply(initism: String) = new Initialism(initism.toLowerCase.trim, initism)
}