package com.justinyan.dictionary

import pureconfig.loadConfig
import pureconfig.generic.auto._

case class DictionaryConfig(httphost: String, httpport: Int)
object DictionaryConfig {
  def load(): DictionaryConfig = loadConfig[DictionaryConfig] match {
      case Right(config) => config
      case Left(error) =>
        throw new RuntimeException("Cannot read config file, errors:\n" + error.toList.mkString("\n"))
    }
}
