name := "scala-minesweeper-core"
organization := "de.htwg.scala-minesweeper"
version := "0.1-SNAPSHOT"

scalaVersion := "2.12.4"

// When running tests, we use this configuration
javaOptions in Test += s"-Dconfig.file=${sourceDirectory.value}/test/resources/application.conf"
// We need to fork a JVM process when testing so the Java options above are applied
fork in Test := true

libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.1"
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.18.0"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.12" % "2.4.16"
libraryDependencies += "com.typesafe.akka" % "akka-remote_2.12" % "2.4.16"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.4.16" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

libraryDependencies += "de.htwg.scala-minesweeper" %% "scala-minesweeper-api" % "0.1-SNAPSHOT"