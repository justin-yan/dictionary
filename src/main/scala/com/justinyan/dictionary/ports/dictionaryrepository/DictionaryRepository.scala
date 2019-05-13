package com.justinyan.dictionary.ports.dictionaryrepository

import com.justinyan.dictionary.domain.types._

import scala.util.Try

trait DictionaryRepository {
  def createExposition(exposition: Exposition): Try[Exposition]
  def getExposition(exposition: Exposition): Try[Exposition]
  def updateExposition(exposition: Exposition): Try[Exposition]
  def deleteExposition(exposition: Exposition): Try[Exposition]

  def insertTerm(term: Term): Try[Term]
  def getTerm(term: Term): Try[Term]
  def updateTerm(term: Term): Try[Term]
  def deleteTerm(term: Term): Try[Term]

  def insertInitialism(initialism: Initialism): Try[Initialism]
  def getInitialism(initialism: Initialism): Try[Initialism]
  def updateInitialism(initialism: Initialism): Try[Initialism]
  def deleteInitialism(initialism: Initialism): Try[Initialism]

  def createDefinition(definition: Definition): Try[Definition]
  def getDefinition(definition: Definition): Try[Definition]
  def updateDefinition(definition: Definition): Try[Definition]
  def deleteDefinition(definition: Definition): Try[Definition]

  // Impl should set the values and then return the full expansion
  def addExpansion(initialism: Initialism, term: Term): Try[Expansion]
  // Impl should fetch the expansion based on the specified initialism
  def getExpansion(initialism: Initialism): Try[Expansion]
  def updateExpansion(expansion: Expansion): Try[Expansion]
  def deleteExpansion(expansion: Expansion): Try[Expansion]
}
