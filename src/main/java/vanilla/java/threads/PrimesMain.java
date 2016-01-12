package vanilla.java.threads;

import java.util.stream.IntStream;

/**
 * Created by peter.lawrey on 12/01/2016.
 */
public class PrimesMain {
    public static void main(String[] args) {
        for (int t = 0; t < 7; t++) {
            long start = System.currentTimeMillis();
            int[] ints = IntStream.range(2, 1000000)
                    .parallel()
                    .filter(i -> i == 2 || (i & 1) != 0)
                    .filter(i -> {
                        int max = (int) Math.sqrt(i);
                        return IntStream.range(3 / 2, (max - 1) / 2)
//                                .parallel()
                                .noneMatch(j -> i % (j * 2 + 1) == 0);
                    })
                    .toArray();
            long time = System.currentTimeMillis() - start;
            System.out.printf("Took %.3f seconds%n", time / 1e3);
        }
    }
}
