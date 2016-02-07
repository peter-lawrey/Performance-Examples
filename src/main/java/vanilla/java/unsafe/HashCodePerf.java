package vanilla.java.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by peter.lawrey on 07/02/2016.
 */
/*
Object.hashCode takes 80 ns on average
Object.hashCode takes 42 ns on average
Object.hashCode takes 42 ns on average
Object.hashCode takes 43 ns on average
Object.hashCode takes 45 ns on average
 */
public class HashCodePerf {
    static final Unsafe UNSAFE;
    static int keep;

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
        Object o = new Object();
        int runs = 20_000_000;
        for (int t = 0; t < 5; t++) {
            long start = System.nanoTime();
            for (int i = 0; i < runs; i++) {
                UNSAFE.putInt(o, 1L, 0);
                keep = o.hashCode(); // recompute the hashCode
            }
            long time = System.nanoTime() - start;
            System.out.printf("Object.hashCode takes %,d ns on average%n", time / runs);
        }
    }
}
