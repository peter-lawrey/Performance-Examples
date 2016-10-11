package vanilla.java.floatingpoint;

/**
 * Created by peter on 11/10/2016.
 */
public class DecodingFPMain {
    public static String printFP(double d) {
        long l = Double.doubleToRawLongBits(d);
        boolean sign = l >= 0;
        int exponent = (int) ((l >> 52) & ((1 << 11) - 1));
        exponent -= 1023;
        long manitissa = l & ((1L << 52) - 1);
        manitissa += 1L << 52;
        String manitissaText = "1." + Long.toBinaryString(manitissa).substring(1);
        manitissaText = manitissaText.replaceAll("0+$", "");
        return d + " == " + (sign ? "+" : "-") + manitissaText + "*2^" + exponent;
    }

    public static void main(String[] args) {
        System.out.println(printFP(100));
        System.out.println(printFP(10));
        System.out.println(printFP(5));
        System.out.println(printFP(3));
        System.out.println(printFP(2));
        System.out.println(printFP(1));
        System.out.println(printFP(0.5));

//        System.out.println(printFP(0.1));
//        System.out.println(printFP(0.2));
//        System.out.println(printFP(0.3));

//        System.out.println(printFP(Double.NEGATIVE_INFINITY));
//        System.out.println(printFP(-0.0));
//        System.out.println(printFP(0.0));
//        System.out.println(printFP(Double.POSITIVE_INFINITY));
//        System.out.println(printFP(Double.NaN));
    }
}
