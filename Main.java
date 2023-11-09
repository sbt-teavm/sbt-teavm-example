package example;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Main {

  private static final float OTHER_NAN = Float.intBitsToFloat(Float.floatToIntBits(Float.NaN) + 1);


  public static void main(String[] args) {
    testFloat();
  }

  static void testFloat() {
    assertEquals(23, Float.parseFloat("23"), 1E-12F);
    assertEquals(23, Float.parseFloat("23.0"), 1E-12F);
    assertEquals(23, Float.parseFloat("23E0"), 1E-12F);
    assertEquals(23, Float.parseFloat("2.30000E1"), 1E-12F);
    assertEquals(23, Float.parseFloat("0.23E2"), 1E-12F);
    assertEquals(23, Float.parseFloat("0.000023E6"), 1E-12F);
    assertEquals(23, Float.parseFloat("00230000e-4"), 1E-12F);
    assertEquals(23, Float.parseFloat("2300000000000000000000e-20"), 1E-12F);
    assertEquals(23, Float.parseFloat("2300000000000000000000e-20"), 1E-12F);
    assertEquals(23, Float.parseFloat("2300000000000000000000e-20"), 1E-12F);
    assertEquals(23, Float.parseFloat("23."), 1E-12F);
    assertEquals(0.1F, Float.parseFloat("0.1"), 0.001F);
    assertEquals(0.1F, Float.parseFloat(".1"), 0.001F);
    assertEquals(0.1F, Float.parseFloat(" .1"), 0.001F);
    assertEquals(0.1F, Float.parseFloat(".1 "), 0.001F);
    assertEquals(-23, Float.parseFloat("-23"), 1E-12F);
    assertEquals(0, Float.parseFloat("0.0"), 1E-12F);
    assertEquals(0, Float.parseFloat("0"), 1E-12F);
    assertEquals(0, Float.parseFloat("00"), 1E-12F);
    assertEquals(0, Float.parseFloat(".0"), 1E-12F);
    assertEquals(0, Float.parseFloat("0."), 1E-12F);
    assertEquals(0, Float.parseFloat("23E-8000"), 1E-12F);
    assertEquals(0, Float.parseFloat("00000"), 1E-12F);
    assertEquals(0, Float.parseFloat("00000.0000"), 1E-12F);
    assertEquals(4499999285F, Float.parseFloat("4499999285"), 100F);
    assertEquals(0.4499999285F, Float.parseFloat("0.4499999285"), 1E-9F);
  }

  static void testBigDecimal() {
    String a = "1231212478987482988429808779810457634781384756794987";
    int aScale = -15;
    String b = "747233429293018787918347987234564568";
    int bScale = 10;
    String c = "1231212478987482988429808779810457634781459480137916301878791834798.7234564568";
    int cScale = 10;
    BigDecimal aNumber = new BigDecimal(new BigInteger(a), aScale);
    BigDecimal bNumber = new BigDecimal(new BigInteger(b), bScale);
    BigDecimal result = aNumber.add(bNumber);
    assertEquals("incorrect value", c, result.toString());
    assertEquals("incorrect scale", cScale, result.scale());
  }

  static void assertEquals(float x1, float x2, float delta) {
    if(x1 == x2) {
      System.out.println(x1 + " == " + x2);
    } else {
      throw new RuntimeException(x1 + " != " + x2);
    }
  }


  static void assertEquals(Object c, Object x1, Object x2) {
    if(x1.equals(x2)) {
      System.out.println(x1.toString() + " == " + x2.toString() + " " + c);
    } else {
      throw new RuntimeException(x1.toString() + " != " + x2.toString() + " " + c);
    }
  }
}
