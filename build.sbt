name := "dar-backend"

version := "0.1"

scalaVersion := "2.12.10"

lazy val phantomVersion    = "2.42.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.3",
  "com.typesafe.akka" %% "akka-slf4j" % "2.6.3",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-http"   % "10.1.11",
  "com.typesafe.akka" %% "akka-stream" % "2.6.3",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11",
  //cassandra
  "com.outworkers"         %% "phantom-dsl"          % phantomVersion,
  "org.scala-lang"    % "scala-reflect"              % scalaVersion.value,
)