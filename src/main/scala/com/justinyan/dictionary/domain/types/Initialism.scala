package com.justinyan.dictionary.domain.types

import scala.util.Try

case class Initialism(canonicalForm: String)

object Initialism {
  def from(rawIni: String): Try[Initialism] = {
    Try(
      rawIni match {
        case s if rawIni.replaceAll("\\s", "") != rawIni => throw new IllegalArgumentException("Initialism must not have whitespace")
        case s if rawIni.toUpperCase != rawIni => throw new IllegalArgumentException("Initialism must be uppercase")
        case _ => Initialism(rawIni)
      }
    )
  }
}