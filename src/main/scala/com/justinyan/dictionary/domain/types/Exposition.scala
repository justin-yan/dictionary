package com.justinyan.dictionary.domain.types

case class Exposition(exposition: String)

object Exposition {
  def from(rawExposition: String): Exposition = Exposition(rawExposition)
}
