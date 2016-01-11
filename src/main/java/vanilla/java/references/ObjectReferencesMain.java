package vanilla.java.references;

import static vanilla.java.references.ShowFields2Main.UNSAFE;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class ObjectReferencesMain {
    public static void main(String[] args) {
/*
        String s = "hello";
        String t = new String("hello");
        String u = t.intern();
        System.out.println(s == t);
        System.out.println(s ==u);

        synchronized ("one") {

        }
*/
        Integer[] objs = new Integer[10];
        for (int i = 0; i < objs.length; i++)
            objs[i] = i * 30;

        printAddresses(objs);

        System.gc();

        printAddresses(objs);
    }

    private static void printAddresses(Object[] objs) {
        for (int i = 0; i < objs.length; i++) {
            int address = UNSAFE.getInt(objs,
                    UNSAFE.ARRAY_OBJECT_BASE_OFFSET + i * UNSAFE.ARRAY_OBJECT_INDEX_SCALE);
            System.out.println(i + " address: " + Integer.toHexString(address));
        }
    }
}
