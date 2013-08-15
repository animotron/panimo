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
//    "securesocial" %% "securesocial" % "2.0.12",
//    "com.imaginea" %% "socket.io.play" % "0.0.3-SNAPSHOT"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
//    resolvers += "OSS Repo" at "https://oss.sonatype.org/content/repositories/snapshots",
//    resolvers += Resolver.url("sbt-plugin-snapshots", url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)
//    "org.neo4j" % "neo4j-kernel" % "1.9.RC1"
  )

}
