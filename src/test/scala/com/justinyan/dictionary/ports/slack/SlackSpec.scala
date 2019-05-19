package com.justinyan.dictionary.ports.slack

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.justinyan.dictionary.domain.DictionarySystem
import com.justinyan.dictionary.domain.types.{Exposition, Term}
import com.justinyan.dictionary.ports.dictionaryrepository.{DictionaryRepository, InMemDictionaryRepository}
import com.justinyan.dictionary.ports.slack
import org.scalatest._
import akka.http.scaladsl.model.FormData
import com.justinyan.dictionary.ports.slack.types.SlashResponse

class SlackSpec extends WordSpec with Matchers with BeforeAndAfter with ScalatestRouteTest {

  var entryRepository: DictionaryRepository = _
  var dictionarySystem: DictionarySystem = _
  var slackPort: Route = _

  before {
    entryRepository = new InMemDictionaryRepository()
    dictionarySystem = new DictionarySystem(entryRepository)
    slackPort = slack.route(dictionarySystem)
  }

  "A SlackPort" should {
    "Not handle invalid requests" in {
      Get() ~> slackPort ~> check {
        handled shouldBe false
      }
      Post() ~> slackPort ~> check {
        handled shouldBe false
      }
      Post("/slack/v0/slash") ~> slackPort ~> check {
        handled shouldBe false
      }
      Post("/slack/v0/slash", FormData("command" -> "2", "text" -> "3")) ~> slackPort ~> check {
        handled shouldBe false
      }
    }

    "handle structurally sound requests" in {
      Post("/slack/v0/slash", FormData("token" -> "1", "command" -> "2", "text" -> "3")) ~> slackPort ~> check {
        handled shouldBe true
        responseAs[SlashResponse].text shouldEqual "Invalid Command"
      }
      Post("/slack/v0/slash", FormData("token" -> "1", "command" -> "define", "text" -> "term = expo")) ~> slackPort ~> check {
        handled shouldBe true
        responseAs[SlashResponse].text shouldEqual "New definition created"
      }
      Post("/slack/v0/slash", FormData("token" -> "1", "command" -> "lookup", "text" -> "term")) ~> slackPort ~> check {
        handled shouldBe true
        responseAs[SlashResponse].attachments.head.title shouldEqual "term"
      }
    }
  }

  "Defn Parser" should {
    "Parse valid commands" in {
      assert(slack.parseDefinition("(testctx) testtrm = testexpo").get.equals(("testctx", Term.from("testtrm"), Exposition.from("testexpo"))))
      assert(slack.parseDefinition("testtrm = testexpo").get.equals((null, Term.from("testtrm"), Exposition.from("testexpo"))))
      assert(slack.parseDefinition("testtrm,123 = testexpo fepou").get.equals((null, Term.from("testtrm,123"), Exposition.from("testexpo fepou"))))
      assert(slack.parseDefinition("(testctx)) testtrm = testexpo").get.equals(("testctx", Term.from(") testtrm"), Exposition.from("testexpo"))))
    }
    "NOT succeed on invalid commands" in {
      assert(slack.parseDefinition("(testctx) testtrm").isFailure)
    }
  }

  "Lookup Parser" should {
    "Parse all commands" in {
      assert(slack.parseLookup("(testctx) testtrm").equals(("testctx", Term.from("testtrm"))))
      assert(slack.parseLookup("testtrm").equals((null, Term.from("testtrm"))))
    }
  }
}
