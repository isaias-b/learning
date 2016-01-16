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

lazy val common = project.in(file("common"))
  .settings(commonSettings:_*)

lazy val app = project.in(file("app"))
  .settings(commonSettings:_*)

lazy val main = project.in(file("."))
  .aggregate(common, app)
  
  
