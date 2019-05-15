package com.justinyan.dictionary.ports.dictionaryrepository

import com.justinyan.dictionary.domain.types.{Definition, Expansion, Exposition, Initialism, Term}

import scala.collection.mutable
import scala.util.{Success, Try}


class InMemDictionaryRepository extends DictionaryRepository {
  // TODO: impl definitively broken for updates, deletes, and many-to-many, but we don't have to deal with that yet
  val expansionStorage = new mutable.HashMap[Initialism, Term]()
  val definitionStorage = new mutable.HashMap[Term, Exposition]()

  def insertExposition(exposition: Exposition): Try[Exposition] = Try(exposition)

  def insertTerm(term: Term): Try[Term] = Try(term)

  def insertInitialism(initialism: Initialism): Try[Initialism] = Try(initialism)

  def insertDefinition(context: String, term: Term, exposition: Exposition): Try[Definition] = {
    definitionStorage(term) = exposition
    Try(Definition(term, exposition, List(), ""))
  }
  def getDefinition(context: String, term: Term): Try[List[Definition]] = {
    Try(definitionStorage.get(term) match {
      case Some(e) => List(Definition(term, e, List(), ""))
      case None => throw new IllegalArgumentException("Term cannot be found")
    })
  }

  def addExpansion(context: String, initialism: Initialism, term: Term): Try[Expansion] = {
    expansionStorage(initialism) = term
    Try(Expansion(initialism, term, List(), ""))
  }
  def getExpansion(context: String, initialism: Initialism): Try[List[Expansion]] = {
    Try(expansionStorage.get(initialism) match {
      case Some(t) => List(Expansion(initialism, t, List(), ""))
      case None => throw new IllegalArgumentException("Term cannot be found")
    })
  }
}
