package vanilla.java.threads;

import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

/**
 * Created by peter.lawrey on 26/11/2015.
 */
public class PrimeNumbersStreamsMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        long primes =
                IntStream.rangeClosed(2, 500_000_000)
                        .parallel()
                        .filter(i -> isPrime(i))
                        .count();

        long time = System.currentTimeMillis() - start;
        System.out.printf("Took %.3f seconds%n", time / 1e3);
        System.out.println("Primes= " + primes);
    }

    static boolean isPrime(int i) {
        if ((i & 1) == 0)
            return false;
        for (int j = 3; j <= (int) Math.sqrt(i); j += 2)
            if (i % j == 0)
                return false;
        return true;
    }
}
