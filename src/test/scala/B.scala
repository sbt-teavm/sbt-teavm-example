package foo

import org.teavm.junit.SkipJVM
import org.teavm.junit.TeaVMTestRunner
import org.junit.runner.RunWith
import org.junit.Test
import org.junit.Assert.*
import org.teavm.classlib.PlatformDetector.*

@SkipJVM
@RunWith(classOf[TeaVMTestRunner])
class B {

  @Test
  def simpleFields(): Unit = {
    println("properties = \n" + sys.props.filter(_._1.contains("teavm")).toList.sorted.mkString("\n"))
    println(
      "Scala test " + System.getProperty("os.name")
    )
    println(Seq(
      "isWebAssembly" -> isWebAssembly(),
      "isJavaScript" -> isJavaScript(),
      "isC" -> isC(),
      "isLowLevel" -> isLowLevel(),
    ))
    val x = 2
    val y = 3
    assertEquals(5, x + y)
  }
}

