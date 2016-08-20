lazy val ghx = project in file(".")

organization in Global := "com.dwijnand"
     version in Global := "1.0.0-SNAPSHOT"
    licences in Global := Seq(Apache2)
   startYear in Global := Some(2016)
 description in Global := "GitHub Extras"
  developers in Global := List(Developer("dwijnand", "Dale Wijnand", "dale wijnand gmail com", url("https://dwijnand.com")))
     scmInfo in Global := Some(ScmInfo(url("https://github.com/dwijnand/ghx"), "scm:git:git@github.com:dwijnand/ghx.git"))

scala211 in Global := "2.11.8"

      scalaVersion := scala211.value
crossScalaVersions := Seq(scala211.value)

enablePlugins(ScalaJSPlugin)

 scalaJSUseRhino in Global := false
isScalaJSProject in Global := false

       maxErrors := 15
triggeredMessage := Watched.clearWhenTriggered

scalacOptions ++= "-encoding utf8"
scalacOptions ++= "-deprecation -feature -unchecked -Xlint"
scalacOptions  += "-language:experimental.macros"
scalacOptions  += "-language:higherKinds"
scalacOptions  += "-language:implicitConversions"
scalacOptions  += "-language:postfixOps"
scalacOptions  += "-Xfuture"
scalacOptions  += "-Yno-adapted-args"
scalacOptions  += "-Ywarn-dead-code"
scalacOptions  += "-Ywarn-numeric-widen"
scalacOptions  += "-Ywarn-unused"
scalacOptions  += "-Ywarn-unused-import"
scalacOptions  += "-Ywarn-value-discard"

scalacOptions in (Compile, console) -= "-Ywarn-unused-import"
scalacOptions in (Test,    console) -= "-Ywarn-unused-import"

libraryDependencies += "fr.hmil" %%% "roshttp" % "1.0.1"

initialCommands in console += "\nimport ghx._"

             fork in Test := false
      logBuffered in Test := false
parallelExecution in Test := true

         fork in run := true
cancelable in Global := true

noDocs
noArtifacts

watchSources ++= (baseDirectory.value * "*.sbt").get
watchSources ++= (baseDirectory.value / "project" * "*.scala").get
