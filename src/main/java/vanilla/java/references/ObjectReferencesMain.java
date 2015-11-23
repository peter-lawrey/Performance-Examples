package vanilla.java.references;

import static vanilla.java.references.ShowFields2Main.UNSAFE;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class ObjectReferencesMain {
    public static void main(String[] args) {
        Object[] objs = new Object[10];
        for (int i = 0; i < objs.length; i++)
            objs[i] = new Object();

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
