package test.lang;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by peter on 10/03/16.
 */
public class ObjectTest {
    boolean[] booleans = {true, false};
    byte[] bytes = {1, 2, 3};
    char[] chars = "Hello World".toCharArray();
    short[] shorts = {111, 222, 333};
    float[] floats = {1.0f, 2.2f, 3.33f, 44.44f, 55.555f, 666.666f};
    int[] ints = {1, 22, 333, 4_444, 55_555, 666_666};
    double[] doubles = {Math.PI, Math.E};
    long[] longs = {System.currentTimeMillis(), System.nanoTime()};
    String[] words = "The quick brown fox jumps over the lazy dog".split(" ");

    @Test
    public void testToString() throws IllegalAccessException {

        Map<String, Object> arrays = new LinkedHashMap<>();
        for (Field f : getClass().getDeclaredFields())
            arrays.put(f.getName(), f.get(this));
        arrays.entrySet().forEach(System.out::println);
    }
}
