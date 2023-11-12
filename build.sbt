scalaVersion := "2.13.12"

enablePlugins(SbtTeaVM)

scalacOptions ++= Seq(
  "-deprecation",
  "-Xsource:3",
)

libraryDependencies ++= Seq(
  "org.teavm" % "teavm-junit" % "0.9.0" % Test,
  "com.github.sbt" % "junit-interface" % "0.13.3" % Test
)

Test / fork := true

Test / javaOptions ++= {
  val browser = "browser-chrome"
  val src1 = (Compile / sourceDirectories).value
  val src2 = (Test / sourceDirectories).value

  Seq(
    "teavm.junit.target" -> (target.value / "teavm-test-target").getAbsolutePath,
    "teavm.junit.js" -> "true",
    "teavm.junit.js.runner" -> browser,
    "teavm.junit.minified" -> "false",
    "teavm.junit.optimized" -> "false",
    "teavm.junit.js.decodeStack" -> "false",
    "teavm.junit.wasm" -> "true",
    "teavm.junit.wasm.runner" -> browser,
    "teavm.junit.wasi" -> "true",
    "teavm.junit.wasi.runner" -> "./run-wasi.sh",
    "teavm.junit.c" -> "true",
    "teavm.junit.c.compiler" -> "./compile-c-unix-fast.sh",
    "teavm.junit.sourceDirs" -> {
      Seq(src1, src2).flatten.map(_.getAbsolutePath).mkString(java.io.File.pathSeparator)
    }
  ).map { case (k, v) => s"-D${k}=${v}" }
}
