package vanilla.java.references;


import static vanilla.java.references.ShowFields2Main.UNSAFE;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class HashcodeMain {
    public static void main(String[] args) {
        Object o = new Object();
        int hc0 = UNSAFE.getInt(o, 1L);
        System.out.println(Integer.toHexString(hc0));

        System.out.println(o);
        System.out.println(o);

        int hc = UNSAFE.getInt(o, 1L);
        System.out.println(Integer.toHexString(hc));

        UNSAFE.putInt(o, 1L, 0);
        System.out.println(o);
        System.out.println(Integer.toHexString(System.identityHashCode(o)));

        String s = "hello";
        System.out.println(s.hashCode());
        System.out.println(System.identityHashCode(s));
        System.out.println(UNSAFE.getInt(s, 1L));

        /*
        String s = "hello";
        String s2 = new String("hello");
        System.out.println(s.hashCode());
        System.out.println(s2.hashCode());

        UNSAFE.putInt(s, 1L, 1);
        UNSAFE.putInt(s2, 1L, 2);
        System.out.println(System.identityHashCode(s));
        System.out.println(System.identityHashCode(s2));
        */
    }
}
