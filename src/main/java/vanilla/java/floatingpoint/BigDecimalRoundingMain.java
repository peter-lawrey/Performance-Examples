package vanilla.java.floatingpoint;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by peter on 13/10/2016.
 */
public class BigDecimalRoundingMain {
    public static void main(String[] args) {
        BigDecimal seven = BigDecimal.valueOf(7);
        BigDecimal bd = BigDecimal.ONE
                .divide(seven, MathContext.DECIMAL128)
                .multiply(seven, MathContext.DECIMAL128);
        System.out.println(bd);

        double d = 1.0 / 49 * 49;
        d = Math.round(d * 100) / 100.0;
        System.out.println(d);
    }
}
