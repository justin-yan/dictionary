package com.justinyan.dictionary.domain.types

// TODO: Figure out how type system can enforce context requirement on all commands
sealed trait Command
sealed trait Lookup extends Command
sealed trait Define extends Command

case class LookupInitialism(context: String, initialism: Initialism) extends Lookup
case class LookupTerm(context: String, term: Term) extends Lookup

case class DefineInitialism(context: String, initialism: Initialism, term: Term) extends Define
case class DefineTerm(context: String, term: Term, exposition: Exposition) extends Define
case class DefineAll(context: String, initialism: Initialism, term: Term, exposition: Exposition) extends Define
