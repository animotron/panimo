import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "panimo"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    //cache
    "org.neo4j" % "neo4j-kernel" % "1.9.2"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
//    "org.neo4j" % "neo4j-kernel" % "1.9.RC1"
  )

}
