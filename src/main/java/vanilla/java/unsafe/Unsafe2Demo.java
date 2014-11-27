package vanilla.java.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by peter on 24/11/2014.
 */
public class Unsafe2Demo {

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
        Field value = String.class.getDeclaredField("value");
        value.setAccessible(true);
        value.set("Hello", "Salut".toCharArray());

        System.out.println("Hello");

        synchronized ("one") {

        }
/*
        Integer x = 5;
        Field value = Integer.class.getDeclaredField("value");
        value.setAccessible(true);
        value.setInt(5, 6);

        Integer i = 3 + 2;
        System.out.println("3 + 2 = "+i);
*/

/*
        MyType mt = new MyType();
        Field num = MyType.class.getDeclaredField("num");
        Field num2 = MyType.class.getDeclaredField("num2");
        long numOff = UNSAFE.objectFieldOffset(num);
        long num2Off = UNSAFE.objectFieldOffset(num2);

        mt.num = 1234;
        mt.num2 = 1234;
        System.out.printf("mt.num=%x%n", UNSAFE.getInt(mt, numOff));
        System.out.printf("mt.num2=%x%n", UNSAFE.getInt(mt, num2Off));

        mt.s = "Hello";
        Field s = MyType.class.getDeclaredField("s");
        long sOff = UNSAFE.objectFieldOffset(s);
        System.out.printf("mt.s=%x%n", UNSAFE.getInt(mt, sOff));
*/
    }

    static class MyType {
        String s;
        Integer num;
        Integer num2;
        long time;
        double d;
    }
}
