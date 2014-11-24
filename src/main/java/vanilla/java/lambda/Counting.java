package vanilla.java.lambda;

import java.util.stream.IntStream;

/**
 * Created by peter_2 on 24/11/2014.
 */
public class Counting {
    public static void main(String[] args) {
        IntStream is = IntStream.of(1, 2, 3, 4, 5);
        long count = is.count();
        System.out.println("count is " + count);
    }
}
