package example;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Main {

  public static void main(String[] args) {
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

  static void assertEquals(String c, Object x1, Object x2) {
    if(x1.equals(x2)) {
      System.out.println(x1.toString() + " == " + x2.toString() + " " + c);
    } else {
      throw new RuntimeException(x1.toString() + " != " + x2.toString() + " " + c);
    }
  }
}
