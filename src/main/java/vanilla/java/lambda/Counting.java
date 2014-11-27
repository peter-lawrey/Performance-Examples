package vanilla.java.lambda;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by peter_2 on 24/11/2014.
 */
public class Counting {
    public static void main(String[] args) {
        Stream<Integer> is = Stream.of(1, 2, 3, 4, 5);
        long count = is.count();
        System.out.println("count is " + count);
    }
}
