package vanilla.java.threads;

import java.util.stream.IntStream;

public class SqrtMain {

    public static void main(String[] args) {
        int procs = Runtime.getRuntime().availableProcessors();
        System.out.println("Procs: " + procs);
        long start = System.nanoTime();
        double sum = IntStream.range(0, (int) 300e6)
                .parallel()
                .mapToDouble(Math::sqrt)
                .sum();
        long time = System.nanoTime() - start;
        System.out.printf("Took %.3f seconds%n", time / 1e9);
    }
}
