package vanilla.java.threads;

import java.util.stream.IntStream;

/**
 * Created by peter.lawrey on 12/01/2016.
 */
public class PrintingThreadPoolMain {
    public static void main(String[] args) throws InterruptedException {
        for (int t = 0; t < 10; t++) {
            long start = System.currentTimeMillis();
            IntStream.range(0, 1000)
                    .forEach(System.out::println);
            long time = System.currentTimeMillis() - start;

            long start2 = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                System.out.println(i);
            }
            long time2 = System.currentTimeMillis() - start2;
            System.out.println("Multi-threaded time taken was " + time / 1e3 + " seconds");
            System.out.println("Single threaded time taken was " + time2 / 1e3 + " seconds");
        }
    }
}
