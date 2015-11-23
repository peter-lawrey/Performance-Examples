package vanilla.java.references;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by peter.lawrey on 22/11/2015.
 */
public class ShowFields2Main {
    static final Unsafe UNSAFE;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static void main(String[] args) {
        Stream.of(MyClass.class, MyClass.class.getSuperclass())
                .flatMap(c -> Stream.of(c.getDeclaredFields()))
                .collect(Collectors.groupingBy(UNSAFE::objectFieldOffset, TreeMap::new,
                        Collectors.reducing(null, (n, v) -> v)))
                .entrySet()
                .forEach(System.out::println);
    }
}

