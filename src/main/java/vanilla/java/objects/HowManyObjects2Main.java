package vanilla.java.objects;

import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class HowManyObjects2Main {
    public static void main(String[] args) throws IOException {
        IntStream.range(0, 10)
                .distinct()
                .forEach(System.out::println);
        System.in.read();
    }
}
