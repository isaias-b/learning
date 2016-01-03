name := """play-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
  jdbc,
  "com.typesafe.play" %% "anorm" % "2.4.0",
  "org.postgresql" % "postgresql" % "9.4.1207.jre7",
  cache,
  ws,
  "org.scalaz" %% "scalaz-core" % "7.1.1",
  "com.datastax.cassandra" % "cassandra-driver-core" % "2.1.1",
  "org.apache-extras.cassandra-jdbc" % "cassandra-jdbc" % "1.2.1",
  "com.chrisomeara" % "pillar_2.11" % "2.0.1",
  
  "org.scalatestplus" %% "play" % "1.4.0-M3" % "test"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

EclipseKeys.withSource := true
