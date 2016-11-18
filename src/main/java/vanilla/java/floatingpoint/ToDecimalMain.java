package vanilla.java.floatingpoint;

/**
 * Created by peter on 18/11/2016.
 */
public class ToDecimalMain {
    public static String toDecimal(long l) {
        StringBuilder sb = new StringBuilder();
        do {
            long digit = l % 10;
            sb.append((char) ('0' + digit));
            l /= 10;
        } while (l > 0);

        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(toDecimal(12345));
    }
}
