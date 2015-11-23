package vanilla.java.lambda;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class StreamMain {
    public static void main(String[] args) {
        /*AtomicInteger count = new AtomicInteger();
        IntStream.range(2, 100000)
                .parallel()
                .filter(i -> IntStream.range(2, (int) Math.sqrt(i))
                        .noneMatch(j -> i % j == 0))
                .boxed()
                .peek(i -> count.incrementAndGet())
                .collect(Collectors.toList())
                .stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println("Primes: " + count);
        */
        Map<String, String> cache = new ConcurrentHashMap<>();
        String result = cache.computeIfAbsent("hello", s -> expensive(s));

        String hello = "Hello";
        Integer i = 128;
        Map<String, Map<Integer, String>> cache2 = new ConcurrentHashMap<>();
        String si =
                cache2.computeIfAbsent(hello, s -> new ConcurrentHashMap<>())
                        .computeIfAbsent(i, j -> hello + j);
    }

    private static String expensive(String s) {
        throw new UnsupportedOperationException("todo");
    }
}
