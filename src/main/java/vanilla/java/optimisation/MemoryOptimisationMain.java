package vanilla.java.optimisation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * git clone https://github.com/peter-lawrey/Performance-Examples
 * mvn install
 *
 * Memory optimisation sample program.
 * <p>
 * This is the inefficient version.
 * <p>
 * Created by peter.
 */
public class MemoryOptimisationMain {
    static final Logger LOGGER = Logger.getLogger(MemoryOptimisationMain.class.getName());

    public static void main(String... args) throws ExecutionException, InterruptedException {

        final int range = 1000, samples = 10000, tasks = 1;
        long start = System.nanoTime();
        ConcurrentMap<Integer, AtomicInteger> map = new ConcurrentHashMap<>();
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new ArrayList<>();
        Random rand = new Random();

        https:
//github.com/peter-lawrey/Performance-Examples
        for (int i = 0; i < tasks; i++)
            futures.add(es.submit(() -> {
                for (int j = 0, len = samples / tasks; j < len; j++) {
                    int next = rand.nextInt(range);
                    AtomicInteger counter = map.computeIfAbsent(next,
                            k -> new AtomicInteger());
                    int count = counter.incrementAndGet();
                    LOGGER.finest(next + ": " + count);
                }
            }));
        for (Future<?> future : futures) {
            future.get();
        }
        es.shutdown();
        long time = System.nanoTime() - start;
        System.out.println("expected " + samples / range + " vs " + map.get(0) + " and " + map.get(range - 1));
        System.out.printf("Average time per sample %,.1f ns%n", (double) time / samples);
    }
}
