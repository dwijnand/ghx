lazy val ghx = project in file(".") aggregate core

val core = setup(project)

def setup(p: Project) = p settings (name := s"ghx-${p.id}")

organization in Global := "com.dwijnand"
        name in Global := "ghx"
     version in Global := "1.0.0-SNAPSHOT"
    licences in Global := Seq(Apache2)
   startYear in Global := Some(2016)
 description in Global := "GitHub Extras"
  developers in Global := List(Developer("dwijnand", "Dale Wijnand", "dale wijnand gmail com", url("https://dwijnand.com")))
     scmInfo in Global := Some(ScmInfo(url(s"https://github.com/dwijnand/ghx"), "scm:git:git@github.com:dwijnand/ghx.git"))

          scala211 in Global := "2.11.8"
      scalaVersion in Global := scala211.value
crossScalaVersions in Global := Seq(scala211.value)

 scalaJSUseRhino in Global := false
isScalaJSProject in Global := false

enablePlugins(ScalaJSPlugin)

       maxErrors in Global := 15
triggeredMessage in Global := Watched.clearWhenTriggered

scalacOptions in Global ++= "-encoding utf8"
scalacOptions in Global ++= "-deprecation -feature -unchecked -Xlint"
scalacOptions in Global  += "-language:experimental.macros"
scalacOptions in Global  += "-language:higherKinds"
scalacOptions in Global  += "-language:implicitConversions"
scalacOptions in Global  += "-language:postfixOps"
scalacOptions in Global  += "-Xfuture"
scalacOptions in Global  += "-Yno-adapted-args"
scalacOptions in Global  += "-Ywarn-dead-code"
scalacOptions in Global  += "-Ywarn-numeric-widen"
scalacOptions in Global  += "-Ywarn-unused"
scalacOptions in Global  += "-Ywarn-unused-import"
scalacOptions in Global  += "-Ywarn-value-discard"

scalacOptions in Global in console -= "-Ywarn-unused-import"

libraryDependencies in core += "fr.hmil" %%% "roshttp" % "1.0.1"

initialCommands in Global in console += "\nimport ghx._"
initialCommands                      -= "\nimport ghx._" // root project doesn't contain ghx

             fork in Global in Test := false
      logBuffered in Global in Test := false
parallelExecution in Global in Test := true

      fork in Global in run := true
cancelable in Global        := true

noDocs in Global
noArtifacts
