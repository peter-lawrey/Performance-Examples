package vanilla.java.optimisation;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * git clone https://github.com/peter-lawrey/Performance-Examples
 * mvn install
 * <p>
 * Memory optimisation sample program.
 * <p>
 * This is the inefficient version.
 * <p>
 * Created by peter.
 */
public class MemoryOptimisation2Main {
    static final Logger LOGGER = Logger.getLogger(MemoryOptimisation2Main.class.getName());

    public static void main(String... args) throws ExecutionException, InterruptedException {

        for (int t = 0; t < 5; t++) {
            final int range = 1000, samples = 10000000, tasks = 4;
            long start = System.nanoTime();
            Map<Integer, Integer> map = new HashMap<>();
            ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            List<Future<Map<Integer, int[]>>> futures = new ArrayList<>();
            for (int i = 0; i < tasks; i++)
                futures.add(es.submit(() -> {
                    Function<Integer, int[]> mappingFunction = k -> new int[1];
                    Random rand = new Random();
                    Map<Integer, int[]> map2 = new HashMap<>();
                    for (int j = 0; j < samples / tasks; j++) {
                        int next = rand.nextInt(range);
                        int[] counter = map2.computeIfAbsent(next,
                                mappingFunction);
                        counter[0]++;
//                    int count = counter.incrementAndGet();
//                    LOGGER.finest(next + ": " + count);
                    }
                    return map2;
                }));

            for (Future<Map<Integer, int[]>> future : futures) {
                Map<Integer, int[]> map2 = future.get();
                for (Map.Entry<Integer, int[]> entry : map2.entrySet()) {
                    Integer count = map.computeIfAbsent(entry.getKey(), k -> 0);
                    map.put(entry.getKey(), count + entry.getValue()[0]);
                }
            }
            es.shutdown();
            long time = System.nanoTime() - start;
            System.out.println("expected " + samples / range + " vs " + map.get(0) + " and " + map.get(range - 1));
            System.out.printf("Average time per sample %,.1f ns%n", (double) time / samples);
        }
    }
}
