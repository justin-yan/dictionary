package com.justinyan.dictionary.domain.types

case class Definition(term: Term, exposition: Exposition, synonyms: List[Term], context: String)
