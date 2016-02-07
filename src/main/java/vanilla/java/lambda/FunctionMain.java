package vanilla.java.lambda;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class FunctionMain {
    public static void main(String[] args) {
//        IntFunction<Map<String, String>> newMap = € -> new TreeMap<>();
        IntFunction<String> is = (i) -> Integer.toString(i);
        ToIntFunction<String> ls = (s) -> s.length();
        Function<String, String> app = s -> s + " test";
        BiFunction<String, String, String> bsss = String::concat;
    }
}
