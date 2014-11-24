package vanilla.java.unsafe;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by peter on 24/11/2014.
 */
public class Unsafe3Demo {

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

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
        // todo don't do this.
        UNSAFE.getInt(0L);
    }

    static class MyType {
        String s;
        Integer num;
        Integer num2;
        long time;
        double d;
    }
}
