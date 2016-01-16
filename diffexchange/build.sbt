name := """diffexchange"""

val commonSettings = Seq (
  organization		:= "com.bartelborth",
  version			:= "0.1-SNAPSHOT",
  scalaVersion 		:= "2.11.6",
  scalacOptions 	:= Seq("-unchecked", "-deprecation", "-encoding", "utf8")
)

lazy val testDependencies = Seq (
  "org.scalatest" %% "scalatest" % "2.2.0" % "test"
)

lazy val cassandraDependencies = Seq (
  "com.datastax.cassandra" % "cassandra-driver-core" % "2.1.1"
)

lazy val playDependencies = Seq (
  jdbc,
  cache,
  ws,
  specs2
)

lazy val common = project.in(file("common"))
  .settings(commonSettings:_*)
  .settings(libraryDependencies ++= (testDependencies ++ cassandraDependencies))

lazy val app = project.in(file("app"))
  .enablePlugins(PlayScala)
  .settings(commonSettings:_*)
  .settings(libraryDependencies ++= playDependencies)

lazy val main = project.in(file("."))
  .aggregate(common, app)
  
  
