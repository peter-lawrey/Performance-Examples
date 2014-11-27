package vanilla.java;

import java.lang.reflect.Field;

/**
 * Created by peter_2 on 26/11/2014.
 */
public class Puzzle {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer i = 5;
        Field value = Integer.class.getDeclaredField("value");
        value.setAccessible(true);
        value.setInt(i, 6);
        {
            Integer x = 2;
            Integer y = 3;
            Integer z = x + y;
            System.out.println(x + " + " + y + " is " + z);
            Integer a = 5;
            Integer b = 6;
            System.out.println(5 + " + " + b + " is equals " + a.equals(b));
        }
    }
}
