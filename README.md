# dictionary

Dictionary makes it easy for organizations to collaboratively define their unique lingo and make it accessible to anyone who is confused about any terminology.

## Quickstart

### User

### Developer

- `sbt test`
- `sbt clean assemble`
- `sbt run`

## Problem Domain

Dictionary is motivated by the typical onboarding experience most employees have, where they are bombarded with new terms and abbreviations that they've never heard before.

It can take months to finally absorb them all, and oftentimes you don't want to bother people to learn them.  Wiki pages and Google docs are created but quickly succumb to bitrot.

Dictionary takes the approach of having a stand-alone service to manage the data, but to have plugins that integrate with *where the conversations happen*.  Wiki pages bitrot because they are out of the natural flow of conversation and people forget that the page exists, but if you are able to look terms up in Slack, right where someone says it, and you discover the capability naturally when someone else uses it - you now have enough visibility to have a fighting chance of keeping your terminology up to date.


## Roadmap


## Design

The core webservice is built with Scala 2.12, using SBT as the build tool, and Travis for CI.

