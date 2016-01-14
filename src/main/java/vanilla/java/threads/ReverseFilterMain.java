package vanilla.java.threads;

import java.util.stream.IntStream;

/**
 * Created by peter.lawrey on 13/01/2016.
 */
public class ReverseFilterMain {
    public static void main(String[] args) {
        for (int t = 0; t < 5; t++) {
            long start = System.currentTimeMillis();
            ThreadLocal<StringBuilder> sbtl = ThreadLocal.withInitial(StringBuilder::new);
            long count = IntStream.range(1, 100_000_000).parallel()
                    .filter(i -> {
                        StringBuilder sb = sbtl.get();
                        sb.setLength(0);
                        sb.append(i).reverse();
                        return sb.indexOf("128") >= 0;
                    }).count();
            long time = System.currentTimeMillis() - start;
            System.out.printf("Took %.3f seconds to find %,d numbers%n", time / 1e3, count);
        }
    }
}
