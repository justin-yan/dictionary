package com.justinyan.dictionary.domain

import com.justinyan.dictionary.domain.types._
import com.justinyan.dictionary.ports.dictionaryrepository.DictionaryRepository

import scala.util.Try

class DictionarySystem(dictRepo: DictionaryRepository) {

  // TODO: Improve handleCommand type-signature to accurately reflect the Try[Expansion | Definition] return value
  //   Currently, ports responding to handled commands are forced to destructure an AnyRef
  def handleUserCommands(command: Command): Try[AnyRef] = {
    command match {
      case LookupInitialism(context, initialism) => expandInitialism(initialism) // TODO: Use context
      case LookupTerm(context, term) => defineTerm(term) // TODO: Use context
      case DefineInitialism(context, initialism, term) => setExpansion(initialism, term) // TODO: Use context
      case DefineTerm(context, term, exposition) => setDefinition(term, exposition) // TODO: Use context
      case DefineAll(context, initialism, term, exposition) => ??? // TODO: Implement
    }
  }

  /** BLOCK: Application User Use Cases
    * These methods enable people to define and look-up their initialisms and terms
    */
  private def setExpansion(initialism: Initialism, term: Term): Try[Expansion] = {
    // TODO - handle this transactionally?
    // TODO - what if any part of these legs already exist? Overwrite?
    dictRepo.insertInitialism(initialism)
    dictRepo.insertTerm(term)
    dictRepo.addExpansion(initialism, term)
  }
  private def expandInitialism(initialism: Initialism): Try[Expansion] = {
    dictRepo.getExpansion(initialism)
  }

  private def setDefinition(term: Term, exposition: Exposition): Try[Definition] = {
    // TODO - handle this transactionally?
    // TODO - what if any part of these legs already exist? Overwrite?
    dictRepo.insertTerm(term)
    dictRepo.insertExposition(exposition)
    dictRepo.insertDefinition(term, exposition)
  }
  private def defineTerm(term: Term): Try[Definition] = {
    dictRepo.getDefinition(term)
  }

  /** BLOCK: Admin Use Cases
    * These methods enable admins to curate the quality of a dictionary
    */

  // DELETE methods

}
