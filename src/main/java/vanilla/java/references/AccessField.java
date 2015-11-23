package vanilla.java.references;

import java.lang.reflect.Field;

import static vanilla.java.references.ShowFields2Main.UNSAFE;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class AccessField {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        MyClass mc = new MyClass();
        mc.d = 12345.0;
        mc.l = 12345;

        Field d = mc.getClass().getDeclaredField("d");
        d.setAccessible(true);
        Field l = mc.getClass().getDeclaredField("l");
        l.setAccessible(true);

        d.set(mc, 2345.0);
        System.out.println("d: " + d.get(mc));
        System.out.println("l: " + l.get(mc));

        long dOff = UNSAFE.objectFieldOffset(d);
        long lOff = UNSAFE.objectFieldOffset(l);
        System.out.println("l as a long: " + Long.toString(UNSAFE.getLong(mc, lOff)));

    }
}
