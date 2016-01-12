package vanilla.java.memory;

import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Created by peter.lawrey on 11/01/2016.
 */
public class VisualVMMain {
    public static void main(String... args) throws IOException, InterruptedException {
        for (int i = 0; ; i++) {
            if (runTest(i) < 0)
                throw new AssertionError();
        }
    }

    private static long runTest(int i) {
        return IntStream.range(0, 10)
                .map(x -> x + i)
                .count();
    }
}
