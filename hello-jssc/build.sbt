enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)

name := """hello-jssc"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scream3r" % "jssc" % "2.8.0"
)

mainClass in Compile := Some("main.Main")

EclipseKeys.withSource := true
