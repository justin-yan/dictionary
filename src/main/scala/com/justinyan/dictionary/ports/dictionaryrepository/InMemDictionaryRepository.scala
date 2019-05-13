package com.justinyan.dictionary.ports.dictionaryrepository

import scala.collection.mutable
import scala.util.{Try, Success}


class InMemDictionaryRepository extends DictionaryRepository {
  val storage = new mutable.HashMap[String, String]()

}
