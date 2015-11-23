package vanilla.java.lambda;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.*;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class SuppliersMain {
    public static void main(String[] args) {
        BooleanSupplier bs = () -> true;
        IntSupplier is = () -> 1;
        LongSupplier ls = System::currentTimeMillis;
        DoubleSupplier ds = Math::random;
        Supplier<Map<String, String>> ms = TreeMap::new;
    }
}
