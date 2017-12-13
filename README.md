# scala-minesweeper-core

## deploy to local repository for usage in scala-minesweeper-wui
go to your local directory in which scala-minesweeper-core is stored and run "sbt publishM2". Now sbt publishes a jar file of this project into your local m2 directory. Now sbt in scala-minesweeper-wui is able to find the jar file. Maybe its necessary to update project resolvers indexes
