package vanilla.java.threads;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by peter.lawrey on 26/11/2015.
 */
public class PrimeNumbersStreamsMain {
    final ConcurrentMap<String, String> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<String> list = Arrays.asList("a,b,c,d,e,f,g".split(","));
        list.forEach(System.out::println);
        List<Integer> primes =
                IntStream.rangeClosed(2, 10_000_000)
                        .parallel()
                        .filter(i -> isPrime(i))
                        .boxed()
                        .collect(Collectors.toList());

        long time = System.currentTimeMillis() - start;
        System.out.printf("Took %.3f seconds%n", time / 1e3);
        System.out.println("Primes= " + primes.size());
    }

    static boolean isPrime(int i) {
        if ((i & 1) == 0)
            return false;
        for (int j = 3; j <= (int) Math.sqrt(i); j += 2)
            if (i % j == 0)
                return false;
        return true;
    }

    public String cachedExpensive(String s) {
        String value = cache.get(s);
        if (value != null)
            return value;
        synchronized (cache) {
            value = cache.get(s);
            if (value == null)
                cache.put(s, value = expensive(s));
            return value;
        }
    }

    public String cachedExpensive8(String s) {
        return cache.computeIfAbsent(s, this::expensive);
    }

    private String expensive(String s) {
        return s;
    }
}
