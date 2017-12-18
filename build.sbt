name := "scala-minesweeper-core"

version := "0.1"

scalaVersion := "2.12.3"

publishTo := Some(Resolver.file("file", new File(Path.userHome.absolutePath + "/.m2/repository")))

libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.1"
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.18.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"