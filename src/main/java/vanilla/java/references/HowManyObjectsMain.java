package vanilla.java.references;

import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class HowManyObjectsMain {
    public static void main(String[] args) throws IOException {
        IntStream.range(0, 10)
                .distinct()
                .forEach(System.out::println);
        System.in.read();
    }
}
