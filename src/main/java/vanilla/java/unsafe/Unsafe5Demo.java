package vanilla.java.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by peter on 24/11/2014.
 */
public class Unsafe5Demo {

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

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String s = "hello world";
        System.out.println(Integer.toHexString(s.hashCode()));
        System.out.println(Integer.toHexString(System.identityHashCode(s)));
        int stringHashCode = UNSAFE.getInt(s, 1L);
        System.out.println("from header: " + Integer.toHexString(stringHashCode));

        Field hash = String.class.getDeclaredField("hash");
        hash.setAccessible(true);
        int origHashCode = hash.getInt(s);
        System.out.println("from hash: " + Integer.toHexString(origHashCode));

        long offset = UNSAFE.objectFieldOffset(hash);
        System.out.println("offset: " + offset);

        int origHashCode2 = UNSAFE.getInt(s, offset);
        System.out.println("from int at " + offset + ": " + Integer.toHexString(origHashCode2));
        UNSAFE.putInt(s, offset, 0x12345678);
        System.out.println("new string.hashCode is " + Integer.toHexString(s.hashCode()));

    }

    public static long memoryUsed() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    static class MyType {
        String s;
        int num;
        long time;
        double d;
    }
}
