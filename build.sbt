name := "scala-minesweeper-core"
organization := "de.htwg.scala-minesweeper"
version := "0.1-SNAPSHOT"

scalaVersion := "2.12.4"

//publishTo := Some(Resolver.file("file", new File(Path.userHome.absolutePath + "/.m2/repository")))

libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.1"
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.18.0"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.12" % "2.4.16"
libraryDependencies += "com.typesafe.akka" % "akka-remote_2.12" % "2.4.16"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

libraryDependencies += "de.htwg.scala-minesweeper" %% "scala-minesweeper-api" % "0.1-SNAPSHOT"