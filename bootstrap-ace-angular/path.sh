#!/bin/bash

configFile=.PATH
backupFile=.PATH.bak

function realpath { echo "$(cd "$1" ; pwd)"; };

function build {
  newpath=$PATH
  while read p; do
    realp="$(realpath $p)"
    newpath=$newpath:$realp
  done <$configFile
  echo "$newpath"
}

function echo-done {
  echo "done"
}

## path commands

function cmd-save {
  echo "save into $backupFile"
  echo "$PATH" > $backupFile && echo-done
}

function cmd-load {
  echo "load from $backupFile"
  PATH=$(cat $backupFile) && echo-done
}

function cmd-config {
  echo "configure build from $configFile"
  PATH=$(build) && echo-done
}

## path show- commands

function cmd-show-build {
  echo "show build from $configFile = {"
  cat $configFile
  echo "}"
  build | sed 's/:/\n/g'
}

function cmd-show {
  echo "show current \$PATH"
  echo "$PATH" | sed 's/:/\n/g'
}

function cmd-show-bak {
  echo "show $backupFile = {"
  cat $backupFile
  echo "}"
}

## main entry point

function path {
  for var in "$@"
  do
    cmd-$var
  done
}

export -f path

