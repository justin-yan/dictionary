[![Build Status](https://travis-ci.org/justin-yan/dictionary.svg?branch=master)](https://travis-ci.org/justin-yan/dictionary)[![codecov](https://codecov.io/gh/justin-yan/rationals/branch/master/graph/badge.svg)](https://codecov.io/gh/justin-yan/dictionary)[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=dictionary&metric=alert_status)](https://sonarcloud.io/dashboard?id=dictionary)

# dictionary

Dictionary makes it easy for organizations to collaboratively define their unique lingo and make it accessible to anyone who is confused about any terminology.

## Quickstart

### User

Download the JAR and run as follows:

```
java -Dconfig.resource=application.conf -jar dictionaryapp.jar
```

Refer to the `reference.conf` file for the possible configuration of your `application.conf` file.

### Developer

- `sbt test`
- `sbt clean assembly`
- `sbt run`

## Problem Domain

Dictionary is motivated by the typical onboarding experience most employees have, where they are bombarded with new terms and abbreviations that they've never heard before.

It can take months to finally absorb them all, and oftentimes you don't want to bother people to learn them.  Wiki pages and Google docs are created but quickly succumb to bitrot.

Dictionary takes the approach of having a stand-alone service to manage the data, but to have plugins that integrate with *where the conversations happen*.  Wiki pages bitrot because they are out of the natural flow of conversation and people forget that the page exists, but if you are able to look terms up in Slack, right where someone says it, and you discover the capability naturally when someone else uses it - you now have enough visibility to have a fighting chance of keeping your terminology up to date.

## Design

We're using Scala 2.12, SBT as the build tool, and Travis for CI.  The service itself is built in a ports-and-adapters style with akka-http.

```
term                  => should have canonical (lower cased, whitespace stripped, unstemmed) and display versions
definition * <-> *    => this is where synonym and "Context" live
exposition            => contents should be semantically enriched
```

## Roadmap

- Slack port:
    - special syntax for declaring initialism with definition and generating synonym structure
    - integrate with new version of auth
- Clean up dictionaryrepository port:
    - Slick persistence layer
    - Eliminate use of domain types in internal storage
- Config updates to support config choice of repository
- verification concerns (Testing, Observability)
- A web UI
- delivery concerns (Documentation, release, deploy)
- Slack port:
    - pretty formatting
    - public lookups
- Domain:
    - Search, related terms, similar terms
    - Suggestions on empty lookup
- Definition enrichment: auto-link to UI, person references, emoji rendering, summaries vs. full definitions
