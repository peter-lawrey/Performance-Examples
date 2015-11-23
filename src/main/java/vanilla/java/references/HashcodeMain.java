package vanilla.java.references;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class HashcodeMain {
    public static void main(String[] args) {
        /*Object o = new Object();
        int hc0 = UNSAFE.getInt(o, 1L);
        System.out.println(Integer.toHexString(hc0));

        System.out.println(o);

        int hc = UNSAFE.getInt(o, 1L);
        System.out.println(Integer.toHexString(hc));

        String s = "hello";
        String s2 = new String("hello");
        System.out.println(s.hashCode());
        System.out.println(s2.hashCode());

        UNSAFE.putInt(s, 1L, 1);
        UNSAFE.putInt(s2, 1L, 2);
        System.out.println(System.identityHashCode(s));
        System.out.println(System.identityHashCode(s2));
        */
        Date d = new Date(1L * 1000);
        List<Date> dates = new ArrayList<>();
        dates.add(d);
        for (int i = 2; i <= 10; i++) {
            d.setTime(i * 1000);
            dates.add(d);
        }
        System.out.println("dates: " + dates.size() + " " + dates);
    }
}
