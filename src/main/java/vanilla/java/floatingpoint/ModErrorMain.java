package vanilla.java.floatingpoint;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by peter on 13/10/2016.
 */
public class ModErrorMain {
    public static void main(String[] args) {
        BigDecimal tenth = new BigDecimal(0.1);
        BigDecimal ten = BigDecimal.ONE.divide(tenth, MathContext.DECIMAL128);
        System.out.println(ten.subtract(new BigDecimal(99).multiply(tenth)).doubleValue());

        System.out.println(1.0 % 0.1);
    }
}
