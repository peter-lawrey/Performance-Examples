package vanilla.java.math;

import java.util.stream.IntStream;

/**
 * Created by peter_2 on 25/11/2014.
 */
public class Prime {
    boolean isPrime(int n) {
        return (n & 1) != 0 &&
                IntStream.rangeClosed(1, (int) Math.sqrt(n) / 2)
                        .noneMatch(i -> n % (i * 2 + 1) == 0);
    }
}
