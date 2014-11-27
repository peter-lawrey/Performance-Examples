package vanilla.java.optimisation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * Memory optimisation sample program.
 * <p>
 * This is the inefficient version.
 * <p>
 * Created by peter.
 */
public class MemoryOptimisedMain {
    static final Logger LOGGER = Logger.getLogger(MemoryOptimisedMain.class.getName());

    public static void main(String... args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int t = 0; t < 5; t++) {
            final int range = 1000;
            final int samples = 10000000;
            final int tasks = 4;
            long start = System.nanoTime();
            int[] counters0 = new int[range];
            List<Future<int[]>> futures = new ArrayList<>();
            for (int i = 0; i < tasks; i++)
                futures.add(es.submit(() -> {
                    Random rand = new Random();
                    int[] counters = new int[range];
                    for (int j = 0; j < samples / tasks; j++) {
                        int next = rand.nextInt(range);
                        counters[next]++;
                    }
                    return counters;
                }));
            for (Future<int[]> future : futures) {
                int[] counters2 = future.get();
                for (int i = 0; i < counters2.length; i++)
                    counters0[i] += counters2[i];
            }
            long time = System.nanoTime() - start;
            System.out.println("expected " + samples / range + " vs " + counters0[0] + " and " + counters0[range - 1]);
            System.out.printf("Average time per sample %.1f ns%n", (double) time / samples);
        }
        es.shutdown();
    }
}
