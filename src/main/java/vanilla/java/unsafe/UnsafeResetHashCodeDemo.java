package vanilla.java.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by peter on 24/11/2014.
 */
public class UnsafeResetHashCodeDemo {

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
        new MyType();
        memoryUsed();

        long start = memoryUsed();
        MyType mt = new MyType();
        long end = memoryUsed();
        System.out.printf("%s used %,d bytes%n", MyType.class, end - start);
/*
        for (Field field : MyType.class.getDeclaredFields()) {
            long offset = UNSAFE.objectFieldOffset(field);
            System.out.printf("Field %s, offset %d%n", field, offset);
        }

        int hc = UNSAFE.getInt(mt, 1L);
        System.out.printf("Hash code is %x%n", hc);
        {
            int hc1 = mt.hashCode();
            int hc2 = UNSAFE.getInt(mt, 1L);
            System.out.printf("Hash code is %x == %x%n", hc1, hc2);
        }*/

        for (int i = 0; i < 10; i++) {
            UNSAFE.putInt(mt, 1L, i);
            {
                int hc1 = mt.hashCode();
                int hc2 = UNSAFE.getInt(mt, 1L);
                System.out.printf("Hash code is %x == %x%n", hc1, hc2);
            }
        }
    }

    public static long memoryUsed() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }
}
