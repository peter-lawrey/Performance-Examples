package vanilla.java.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by peter.lawrey on 26/11/2015.
 */
public class PrimeNumbersMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<Integer> primes = new ArrayList<>();

        int procs = Runtime.getRuntime().availableProcessors();
        ExecutorService es = Executors.newFixedThreadPool(procs);
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 2; i <= 10_000_000; i++) { ////////
            final int finalI = i;
            futures.add(es.submit(() -> {
                if (isPrime(finalI)) ////////
                    return finalI; ////////
                return null;
            }));
        }
        for (Future<Integer> future : futures) {
            Integer i = future.get();
            if (i != null)
                primes.add(i); ////////
        }
        es.shutdown();

        long time = System.currentTimeMillis() - start;
        System.out.printf("Took %.3f seconds%n", time / 1e3);
        System.out.println("Primes= " + primes.size());
    }

    private static boolean isPrime(int i) {
        if ((i & 1) == 0)
            return false;
        for (int j = 3; j <= (int) Math.sqrt(i); j += 2)
            if (i % j == 0)
                return false;
        return true;
    }
}
