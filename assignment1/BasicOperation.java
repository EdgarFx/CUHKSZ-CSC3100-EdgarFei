package csc3100hw1;
import java.math.*;

public class BasicOperation {
    private static final int DefaultDivScale = 16;

    private BasicOperation(){} //can't Instantiate

    public static double add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).doubleValue();
    }

    public static double sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).doubleValue();
    }

    public static double mul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).doubleValue();
    }

    public static double div(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, DefaultDivScale, RoundingMode.HALF_UP).doubleValue();
    }

    public static double sqrt(String v1) {
        try {
            double result = Math.sqrt(Double.valueOf(v1));
            return result;
        } catch (Exception e) {return 0.0/0;}
    }

    public static double sin(String v1) {
        try {
            double result = Math.sin(Double.valueOf(v1));
            return result;
        } catch (Exception e) {return 0.0/0;}
    }

    public static double cos(String v1) {
        try {
            double result = Math.cos(Double.valueOf(v1));
            return result;
        } catch (Exception e) {return 0.0/0;}
    }

    public static double tan(String v1) {
        try {
            double result = Math.tan(Double.valueOf(v1));
            return result;
        } catch (Exception e) {return 0.0/0;}
    }
}
