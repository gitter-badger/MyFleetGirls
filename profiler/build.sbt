
scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.0",
  "org.json4s" %% "json4s-native" % "3.2.6",
  "com.typesafe.play" %% "play-test" % play.core.PlayVersion.current
)

Keys.fork in Keys.run := true

javaOptions in Keys.run ++= Seq(
  "-agentlib:hprof=cpu=samples,depth=80"
)
