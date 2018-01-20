#!/bin/bash

export WORKING_DIR=`pwd`
echo "> Working dir: $WORKING_DIR"

echo "> Getting data..."
git clone https://github.com/scala-minesweeper/scala-minesweeper-api.git

echo "> Build api dependency"
cd scala-minesweeper
sbt compile publishLocal
cd ..