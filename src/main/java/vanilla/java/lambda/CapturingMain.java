package vanilla.java.lambda;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Consumer;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class CapturingMain {
    public static void main(String[] args) {
        Consumer<String> pa = printMe();
        Consumer<String> pb = printMe();
        System.out.println(pa.getClass());
        System.out.println(pa == pb);
        System.out.println(new HashSet<>(Arrays.asList(pa, pb)));

        Consumer<String> p2a = printMe2();
        Consumer<String> p2b = printMe2();
        System.out.println(p2a == p2b);
        System.out.println(new HashSet<>(Arrays.asList(p2a, p2b)));

        Consumer<String> p3a = s -> System.out.println(s);
        Consumer<String> p3b = s -> System.out.println(s);
        System.out.println(p3a == p3b);
        System.out.println(p3a.equals(p3b));
        System.out.println(new HashSet<>(Arrays.asList(p3a, p3b)));
    }

    static Consumer<String> printMe() {
        return s -> System.out.println(s);
    }

    static Consumer<String> printMe2() {
        return System.out::println;
    }
}
