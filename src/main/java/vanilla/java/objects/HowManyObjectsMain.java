package vanilla.java.objects;

import java.io.IOException;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class HowManyObjectsMain {
    public static void main(String[] args) throws IOException {
        String a = "hello";
        String b = "world";
        String c = a + ' ' + b;
        System.out.println(c);

        System.in.read();
    }
}
