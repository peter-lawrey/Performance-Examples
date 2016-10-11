package vanilla.java.floatingpoint;

import java.math.BigDecimal;

/**
 * Created by peter on 11/10/2016.
 */
public class UsingBigDecimalMain {
    public static void main(String[] args) {
        double a = 0.1;
        double b = 0.2;
        double c = 0.3;
        System.out.println(a + b == c);

        BigDecimal ba = new BigDecimal(a);
        BigDecimal bb = new BigDecimal(b);
        BigDecimal bc = new BigDecimal(c);
        System.out.println("   " + ba +
                "\n + " + bb +
                "\n== " + ba.add(bb) +
                "\n!= " + bc);
    }
}
