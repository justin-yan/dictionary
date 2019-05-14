package com.justinyan.dictionary.domain.types

case class Expansion(initialism: Initialism, term: Term, synonyms: List[Initialism], context: String)
