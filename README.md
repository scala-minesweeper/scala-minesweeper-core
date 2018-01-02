# scala-minesweeper-core

## deploy
Deploy this core artifact to your local repository for usage in scala-minesweeper-wui.
Run ```sbt publishLocal``` in your project root and sbt publishes a jar 
file of this project into your local ivy2 directory. 
Now sbt in scala-minesweeper-wui is able to find the jar file. 

The version number you select must end with SNAPSHOT, or you must change the version number each time you publish. Ivy 
maintains a cache, and it stores even local projects in that cache. If Ivy already has a version cached, it will not 
check the local repository for updates, unless the version number matches a changing pattern, and SNAPSHOT is one such 
pattern.

## usage as dependency

It's important to add the scala version suffix at the end of the artifact name.
```
libraryDependencies += "de.htwg.scala-minesweeper" %% "scala-minesweeper-core_2.12" % "0.1-SNAPSHOT"
```