name := "dictionary"
version := "0.1.0"
organization := "com.justinyan"
scalaVersion := "2.12.8"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"
libraryDependencies += "com.github.pureconfig" %% "pureconfig" % "0.11.0"
libraryDependencies ++= {
  val akkaVersion = "2.5.22"
  val akkaHttp = "10.1.8"
  Seq(
    "com.typesafe.akka" %% "akka-http-core" % akkaHttp,
    "com.typesafe.akka" %% "akka-http" % akkaHttp,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttp,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
  )
}
