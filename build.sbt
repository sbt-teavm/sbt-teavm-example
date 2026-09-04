scalaVersion := "3.9.0"

enablePlugins(SbtTeaVM)

scalacOptions ++= Seq(
  "-deprecation",
)

InputKey[Unit]("copyWASM") := {
  val mainFile = "main.wasm"
  IO.copyFile(crossTarget.value / "teavm" / "wasi" / mainFile, file(mainFile))
}
