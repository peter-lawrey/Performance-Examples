package vanilla.java.lambda;

import org.junit.Assert;

import java.time.LocalDate;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class ConsumerMain {
    public static void main(String[] args) {
        IntConsumer ic = i -> System.out.println(args[i]);
        LongConsumer lc = l -> System.out.println(new Date(l));
        Consumer<LocalDate> ldc = date -> Assert.assertFalse(date.isLeapYear());
    }
}
