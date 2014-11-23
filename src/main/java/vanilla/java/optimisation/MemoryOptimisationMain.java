package vanilla.java.optimisation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Memory optimisation sample program.
 * <p>
 * This is the inefficient version.
 * <p>
 * Created by peter.
 */
public class MemoryOptimisationMain {
    static final Logger LOGGER = Logger.getLogger(MemoryOptimisationMain.class.getName());

    public static void main(String... args) throws ExecutionException, InterruptedException {
        final int range = 1000;
        final int samples = 10000;
        final int tasks = 1;
        long start = System.nanoTime();
        ConcurrentMap<Integer, AtomicInteger> map = new ConcurrentHashMap<>();
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < tasks; i++)
            futures.add(es.submit(() -> {
                for (int j = 0; j < samples / tasks; j++) {
                    int next = rand.nextInt(range);
                    AtomicInteger counter = map.computeIfAbsent(next, k -> new AtomicInteger());
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
