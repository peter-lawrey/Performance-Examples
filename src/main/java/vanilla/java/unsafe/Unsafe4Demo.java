package vanilla.java.unsafe;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by peter on 24/11/2014.
 */
public class Unsafe4Demo {

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

    static final int x = 5;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException, InstantiationException {
        System.out.println("Starting");
        Object o = UNSAFE.staticFieldBase(Unsafe4Demo.class);
        Field _x = Unsafe4Demo.class.getDeclaredField("x");
        _x.setAccessible(true);
        long xOff = UNSAFE.staticFieldOffset(_x);
        UNSAFE.compareAndSwapInt(o, xOff, 5, 42);
        System.out.println("x=" + UNSAFE.getInt(o, xOff) + " x is now " + _x.get(null));

/*
        MyEnum mt = (MyEnum) UNSAFE.allocateInstance(MyEnum.class);
        Field name = Enum.class.getDeclaredField("name");
        name.setAccessible(true);
        name.set(mt, "A");
        System.out.println("Done " + mt);
        System.out.println(mt.equals(MyEnum.A));
*/

    }

    enum MyEnum {
        A, B;
    }

    static class MyType {
        String s;
        Integer num;
        Integer num2;
        long time;
        double d;

        public MyType(Void v) throws InterruptedException {
            Thread.sleep(1000);
        }
    }
}
