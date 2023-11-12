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

Test / javaOptions ++= {
  val browser = "browser-chrome"
  val src1 = (Compile / sourceDirectories).value
  val src2 = (Test / sourceDirectories).value

  Seq(
    "target" -> (crossTarget.value / "teavm-test-target").getAbsolutePath,
    "js" -> "true",
    "js.runner" -> browser,
    "minified" -> "false",
    "optimized" -> "false",
    "js.decodeStack" -> "false",
    "wasm" -> "true",
    "wasm.runner" -> browser,
    "wasi" -> "true",
    "wasi.runner" -> "./run-wasi.sh",
    "c" -> "true",
    "c.compiler" -> "./compile-c-unix-fast.sh",
    "sourceDirs" -> {
      Seq(src1, src2).flatten.map(_.getAbsolutePath).mkString(java.io.File.pathSeparator)
    }
  ).map { case (k, v) => s"-Dteavm.junit.${k}=${v}" }
}
