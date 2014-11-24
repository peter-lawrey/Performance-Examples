package vanilla.java;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by peter_2 on 24/11/2014.
 */
public class UnsafeDemo {

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
        // Thread.currentThread().stop(new Exception());
        UNSAFE.throwException(new Exception());
    }
}
