name := "Avatar"

version := "0.1"

scalaVersion := "2.13.9"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "2.0.0-M3",
  "org.scala-lang.modules" %% "scala-swing" % "2.1.1",
  "junit" % "junit" % "4.12" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test exclude ("junit", "junit-dep"),
  "org.xerial" % "sqlite-jdbc" % "3.36.0.1",
  "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.18",
  "org.apache.commons" % "commons-text" % "1.9",
  // "com.eed3si9n" % "sbt-assembly" % "2.1.1"  
)
//fork := true

// assembly / mainClass := Some("gui.Main") // le nom de la classe main
// assembly / assemblyJarName := "avatar.jar"
