package vanilla.java.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by peter on 24/11/2014.
 */
public class UnsafeIdentityDemo {

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

    public static void setIdentityHashCode(Object o, int code) {
        UNSAFE.putInt(o, 1l, code & 0x7FFF_FFF);
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Double d = 1.0;
        Double d2 = 1.0;
        setIdentityHashCode(d, 1);
        setIdentityHashCode(d2, 1);
        System.out.println("d: " + d +
                " System.identityHashCode(d): " + System.identityHashCode(d));
        System.out.println("d2: " + d2 +
                " System.identityHashCode(d2): " + System.identityHashCode(d2));
        System.out.println("d == d2: " + (d == d2));
    }
}
