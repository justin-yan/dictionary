package com.justinyan.dictionary.ports.dictionaryrepository

import com.justinyan.dictionary.domain.types.{Definition, Exposition, Term}

import scala.collection.mutable
import scala.util.{Success, Try}


class InMemDictionaryRepository extends DictionaryRepository {
  // TODO: impl definitively broken for updates, deletes but we don't have to deal with that yet
  val definitionStorage = new mutable.HashMap[(Term, Context), Exposition]()

  def insertDefinition(term: Term, exposition: Exposition, context: Context = null): Try[Definition] = {
    definitionStorage((term, context)) = exposition
    Try(Definition(term, exposition, List(), ""))
  }
  def getDefinition(term: Term, context: Context = null): Try[Definition] = {
    Try(definitionStorage.get((term, context)) match {
      case Some(e) => Definition(term, e, findSyns(term, e), context)
      case None => throw new IllegalArgumentException("Term cannot be found")
    })
  }
  def getAllDefinitions(term: Term): Try[List[Definition]] = {
    val deflist = definitionStorage.iterator.filter(entry => entry._1._1.equals(term)).toList
    Try(deflist.map(entry => {
      val trm = entry._1._1
      val context = entry._1._2
      val expo = entry._2
      val syns = findSyns(trm, expo)
      Definition(trm, expo, syns, context)
    }))
  }

  // TODO: small bug where the same term with different contexts can appear in the synlist multiple times
  def findSyns(term: Term, expo: Exposition) = definitionStorage.iterator.filter(entry => entry._2.equals(expo) && entry._1._1 != term)
    .map(entry => entry._1._1).toList
}
