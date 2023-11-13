package foo

import org.teavm.junit.SkipJVM
import org.teavm.junit.TeaVMTestRunner
import org.junit.runner.RunWith
import org.junit.Test
import org.junit.Assert.*
import org.teavm.classlib.PlatformDetector.*

@SkipJVM
@RunWith(classOf[TeaVMTestRunner])
class C {

  private def hoge(): String = Seq(
    "os" -> System.getProperty("os.name"),
    "class" -> this.getClass.getName,
    "isWebAssembly" -> isWebAssembly(),
    "isJavaScript" -> isJavaScript(),
    "isC" -> isC(),
    "isLowLevel" -> isLowLevel(),
  ).toString

  @Test
  def test2(): Unit = {
    println(hoge())
  }

  @Test
  def test1(): Unit = {
    println(hoge())
  }
}

