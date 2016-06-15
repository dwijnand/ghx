import SbtMisc._

lazy val ghx = project in file(".")

organization := "com.dwijnand"
     version := "1.0.0-SNAPSHOT"
    licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
   startYear := Some(2016)
 description := "GitHub Extras"
     scmInfo := Some(ScmInfo(url("https://github.com/dwijnand/ghx"), "scm:git:git@github.com:dwijnand/ghx.git"))
    homepage := scmInfo.value map (_.browseUrl)

enablePlugins(ScalaJSPlugin)

   scalaVersion in Global := "2.11.8"
scalaJSUseRhino in Global := false

       maxErrors := 15
triggeredMessage := Watched.clearWhenTriggered

scalacOptions ++= "-encoding utf8"
scalacOptions ++= "-deprecation -feature -unchecked -Xlint"
scalacOptions  += "-language:experimental.macros"
scalacOptions  += "-language:higherKinds"
scalacOptions  += "-language:implicitConversions"
scalacOptions  += "-language:postfixOps"
scalacOptions  += "-Xfuture"
scalacOptions  += "-Yinline-warnings"
scalacOptions  += "-Yno-adapted-args"
scalacOptions  += "-Ywarn-dead-code"
scalacOptions  += "-Ywarn-numeric-widen"
scalacOptions  += "-Ywarn-unused"
scalacOptions  += "-Ywarn-unused-import"
scalacOptions  += "-Ywarn-value-discard"

scalacOptions in (Compile, console) -= "-Ywarn-unused-import"
scalacOptions in (Test,    console) -= "-Ywarn-unused-import"

libraryDependencies += "fr.hmil" %%% "roshttp" % "1.0.0"

initialCommands in console += "\nimport ghx._"

             fork in Test := false
      logBuffered in Test := false
parallelExecution in Test := true

         fork in run := true
cancelable in Global := true

noDocs
noArtifacts

isScalaJSProject in Global := false

watchSources ++= (baseDirectory.value * "*.sbt").get
watchSources ++= (baseDirectory.value / "project" * "*.scala").get
