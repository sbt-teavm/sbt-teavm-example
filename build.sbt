scalaVersion := "2.13.12"

enablePlugins(SbtTeaVM)

scalacOptions ++= Seq(
  "-deprecation",
  "-Xsource:3",
)

libraryDependencies ++= Seq(
  "org.teavm" % "teavm-junit" % "0.9.0" % Test,
  "com.github.sbt" % "junit-interface" % "0.13.3" % Test,
  "org.slf4j" % "slf4j-nop" % "1.7.36" % Test,
)

Test / testOptions += Tests.Argument(
  TestFrameworks.JUnit,
  "-q",
  "-v",
)

Test / fork := true

/*
Test / forkOptions ~= (
  _.withOutputStrategy(
    OutputStrategy.CustomOutput(
      java.io.OutputStream.nullOutputStream()
    )
  )
)
*/

Test / forkOptions ~= (
  _.withOutputStrategy(
    OutputStrategy.StdoutOutput
  )
)

def defineTestTask(taskK: TaskKey[Unit], propertyKey: String) = {
  taskK := {
    val k = s"teavm.junit.${propertyKey}"
    val x1 = (Test / javaOptions).value
    val x2 = x1.filter(_ != s"-D${k}=false")
    assert((x1.size - 1) == x2.size, s"${x1} ${x2}")
    val s = state.value.appendWithSession(
      Seq(
        Test / javaOptions := (x2 :+ s"-D${k}=true")
      )
    )
    Project.extract(s).runTask(Test / test, s)._2
  }
}

val teavmTestWasm = taskKey[Unit]("")
val teavmTestWasi = taskKey[Unit]("")
val teavmTestJS   = taskKey[Unit]("")
val teavmTestC    = taskKey[Unit]("")

Seq[(TaskKey[Unit], String)](
  teavmTestWasm -> "wasm",
  teavmTestWasi -> "wasi",
  teavmTestJS   -> "js",
  teavmTestC    -> "c",
).map{ case (x, y) => defineTestTask(x, y) }

TaskKey[Unit]("teavmTestAll") := {
  teavmTestWasm.value
  teavmTestWasi.value
  teavmTestJS.value
  teavmTestC.value
}


Test / javaOptions ++= {
  val browser = "browser-chrome"
  val src1 = (Compile / sourceDirectories).value
  val src2 = (Test / sourceDirectories).value

  Seq(
    "wasm" -> "false",
    "wasi" -> "false",
    "js"   -> "false",
    "c"    -> "false",

    "target" -> (crossTarget.value / "teavm-test-target").getAbsolutePath,
    "js.runner" -> browser,
    "minified" -> "false",
    "optimized" -> "false",
    "js.decodeStack" -> "false",
    "wasm.runner" -> browser,
    "wasi.runner" -> "./run-wasi.sh",
    "c.compiler" -> "./compile-c-unix-fast.sh",
    "sourceDirs" -> {
      Seq(src1, src2).flatten.map(_.getAbsolutePath).mkString(java.io.File.pathSeparator)
    }
  ).map { case (k, v) => s"-Dteavm.junit.${k}=${v}" }
}
