package vanilla.java.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by plawrey on 12/14/15.
 */
public class Fraction {
    public static String toDecimal(long numerator, int denominator) {
        StringBuilder sb = new StringBuilder();
        sb.append(numerator / denominator);
        numerator %= denominator;
        if (numerator == 0)
            return sb.toString();
        sb.append('.');
        // add digits?
        while (numerator != 0 && (denominator % 2 == 0 || denominator % 5 == 0)) {
            numerator *= 10;
            sb.append(numerator / denominator);
            numerator %= denominator;
            while (numerator % 2 == 0 && denominator % 2 == 0) {
                numerator /= 2;
                denominator /= 2;
            }
            if (numerator % 5 == 0 && denominator % 5 == 0) {
                numerator /= 5;
                denominator /= 5;
            }
        }
        if (numerator > 0) {
            int count = 0;
            BigInteger denBD = BigInteger.valueOf(denominator);
            for (BigInteger bi = BigInteger.TEN; ; bi = bi.multiply(BigInteger.TEN)) {
                count++;
                if (bi.subtract(BigInteger.ONE).mod(denBD).compareTo(BigInteger.ZERO) == 0) {
                    String divide = bi.multiply(BigInteger.valueOf(numerator)).divide(denBD).toString();
                    sb.append('(');
                    while (count-- > divide.length())
                        sb.append('0');
                    sb.append(divide).append(')');
                    break;
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 31; i < 10001; i++) {
            String x = toDecimal(1, i);
            if (x.length() > 1000) {
                System.out.println(i);
                System.out.println(x);
                System.out.println(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(i), 2000, BigDecimal.ROUND_HALF_UP));
                break;
            }
        }
    }
}
