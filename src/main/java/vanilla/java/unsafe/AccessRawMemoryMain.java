package vanilla.java.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by peter on 02/04/16.
 */
public class AccessRawMemoryMain {
    // This only works if a GC doesn't move the object while attempting to access it.
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

    // must be run with -XX:-UseCompressedOops
    // run with: -ea
    public static void main(String[] args) {
        Object i = 0x12345678;

        Object[] obj = {i};
        assert Unsafe.ARRAY_OBJECT_INDEX_SCALE == 8; // 8 bytes per reference.
        long address = UNSAFE.getLong(obj, (long) Unsafe.ARRAY_OBJECT_BASE_OFFSET);
        System.out.printf("%x%n", address);
        for (int j = 0; j < 24; j++)
            System.out.printf("%02x ", UNSAFE.getByte(address + j) & 0xFF);
        System.out.println();

        System.out.printf("`i` is a %s and is %x%n", i.getClass(), i);

        // now some really scary sh!t
        long longClassPointer = UNSAFE.getLong(0L, 8L);
        UNSAFE.putLong(i, 8L, longClassPointer);
        System.out.printf("`i` is now a %s and is %x%n", i.getClass(), i);
    }
}
