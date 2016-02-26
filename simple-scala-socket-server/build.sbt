enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)

name := """simple-scala-socket-server"""

version := "1.0"

scalaVersion := "2.11.6"

mainClass in Compile := Some("main.Main")

EclipseKeys.withSource := true
